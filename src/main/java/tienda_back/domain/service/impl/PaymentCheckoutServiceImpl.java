package tienda_back.domain.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.exception.BusinessException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.service.CartProductService;
import tienda_back.domain.service.CartService;
import tienda_back.domain.service.PaymentCheckoutService;
import tienda_back.domain.service.UserOrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class PaymentCheckoutServiceImpl implements PaymentCheckoutService {

    private final CartService cartService;
    private final CartProductService cartProductService;
    private final UserOrderService userOrderService;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String bankBaseUrl;
    private final String bankLogin;
    private final String bankPassword;
    private final String destinationIban;
    private final String paymentConcept;
    private final BigDecimal shippingFee;
    private final BigDecimal freeShippingThreshold;

    public PaymentCheckoutServiceImpl(
            CartService cartService,
            CartProductService cartProductService,
            UserOrderService userOrderService,
            ObjectMapper objectMapper,
            @Value("${bank.integration.base-url:http://localhost:8081}") String bankBaseUrl,
            @Value("${bank.integration.login:Marta}") String bankLogin,
            @Value("${bank.integration.password:marta123}") String bankPassword,
            @Value("${bank.integration.destination-iban:ES33 0081 5220 0001 2345 6789}") String destinationIban,
            @Value("${bank.integration.concept:Compra Nibiru Home}") String paymentConcept,
            @Value("${checkout.shipping-fee:6.99}") double shippingFee,
            @Value("${checkout.free-shipping-threshold:80}") double freeShippingThreshold) {
        this.cartService = cartService;
        this.cartProductService = cartProductService;
        this.userOrderService = userOrderService;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.bankBaseUrl = normalizeBaseUrl(bankBaseUrl);
        this.bankLogin = bankLogin;
        this.bankPassword = bankPassword;
        this.destinationIban = normalizeIban(destinationIban);
        this.paymentConcept = paymentConcept;
        this.shippingFee = BigDecimal.valueOf(Math.max(shippingFee, 0)).setScale(2, RoundingMode.HALF_UP);
        this.freeShippingThreshold = BigDecimal.valueOf(Math.max(freeShippingThreshold, 0)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public CheckoutPaymentResultDto checkout(CheckoutPaymentDto request) {
        validateRequest(request);

        Cart cart = cartService.getActiveCart(request.userId());
        List<CartProduct> items = cartProductService.getByCart(cart);
        if (items.isEmpty()) {
            throw new BusinessException("El carrito esta vacio.");
        }

        BigDecimal subtotal = calculateSubtotal(items);
        if (subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El total del carrito no es valido.");
        }
        BigDecimal shipping = calculateShipping(subtotal);
        BigDecimal total = subtotal.add(shipping).setScale(2, RoundingMode.HALF_UP);

        String apiToken = loginAndGetApiToken();
        payWithBank(request, total, apiToken);
        registerOrder(cart, total);
        closePaidCartAndCreateNewActiveCart(cart, total);

        return new CheckoutPaymentResultDto("Pago completado", total);
    }

    private void validateRequest(CheckoutPaymentDto request) {
        if (request == null) {
            throw new BusinessException("Solicitud de pago invalida.");
        }

        if (isBlank(request.userId())) {
            throw new BusinessException("El usuario es obligatorio.");
        }
        if (isBlank(request.cardHolder())) {
            throw new BusinessException("El titular de la tarjeta es obligatorio.");
        }
        if (isBlank(request.cardNumber())) {
            throw new BusinessException("El numero de tarjeta es obligatorio.");
        }
        if (isBlank(request.expirationMonth())) {
            throw new BusinessException("La fecha de caducidad es obligatoria.");
        }
        if (isBlank(request.cvv())) {
            throw new BusinessException("El CVV es obligatorio.");
        }
    }

    private BigDecimal calculateSubtotal(List<CartProduct> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartProduct item : items) {
            if (item == null || item.getProduct() == null || item.getProduct().getPrice() == null) {
                continue;
            }

            BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(Math.max(item.getQuantity(), 0));
            total = total.add(price.multiply(quantity));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateShipping(BigDecimal subtotal) {
        if (subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        if (subtotal.compareTo(freeShippingThreshold) >= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return shippingFee;
    }

    private String loginAndGetApiToken() {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("login", bankLogin);
        payload.put("password", bankPassword);

        JsonNode response = sendBankRequest("/api/clients/login", payload, true);
        String apiToken = response.path("apiToken").asText("").trim();

        if (apiToken.isBlank()) {
            throw new BusinessException("No se pudo obtener el token de autorizacion del banco.");
        }

        return apiToken;
    }

    private void payWithBank(CheckoutPaymentDto request, BigDecimal total, String apiToken) {
        ObjectNode payload = objectMapper.createObjectNode();

        ObjectNode autorizacion = objectMapper.createObjectNode();
        autorizacion.put("login", bankLogin);
        autorizacion.put("api_token", apiToken);
        payload.set("autorizacion", autorizacion);

        ObjectNode origen = objectMapper.createObjectNode();
        origen.putNull("id");
        origen.put("number", formatCardNumber(request.cardNumber()));
        origen.put("expirationDate", buildExpirationDate(request.expirationMonth()));
        origen.put("cvv", parseCvv(request.cvv()));
        origen.put("name", request.cardHolder().trim());
        payload.set("origen", origen);

        ObjectNode destino = objectMapper.createObjectNode();
        destino.put("iban", destinationIban);
        payload.set("destino", destino);

        ObjectNode pago = objectMapper.createObjectNode();
        pago.put("importe", total);
        pago.put("concepto", paymentConcept);
        payload.set("pago", pago);

        sendBankRequest("/api/pagoTarjeta", payload, false);
    }

    private JsonNode sendBankRequest(String path, JsonNode payload, boolean parseJsonResponse) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(bankBaseUrl + path))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payload)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() / 100 != 2) {
                throw new BusinessException(extractBankError(response.statusCode(), response.body()));
            }

            if (!parseJsonResponse || response.body() == null || response.body().isBlank()) {
                return objectMapper.createObjectNode();
            }

            return objectMapper.readTree(response.body());
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("No se pudo conectar con el banco: " + ex.getMessage());
        }
    }

    private String extractBankError(int statusCode, String body) {
        if (body != null && !body.isBlank()) {
            try {
                JsonNode node = objectMapper.readTree(body);
                String error = node.path("error").asText("").trim();
                String message = node.path("message").asText("").trim();

                if (!error.isBlank() && !message.isBlank()) {
                    return error + ": " + message;
                }
                if (!error.isBlank()) {
                    return error;
                }
                if (!message.isBlank()) {
                    return message;
                }

                JsonNode details = node.path("details");
                if (details.isArray() && !details.isEmpty()) {
                    return details.get(0).asText("Error en pago");
                }
            } catch (Exception ignored) {
                // body is not JSON
            }
        }

        return "Error en el banco (HTTP " + statusCode + ")";
    }

    private void registerOrder(Cart cart, BigDecimal total) {
        UserOrder order = new UserOrder();
        order.setUser(cart.getUser());
        order.setCart(cart);
        order.setTotal(total.doubleValue());
        order.setDate(new Date());
        order.setStatus("PAID");
        userOrderService.create(order);
    }

    private void closePaidCartAndCreateNewActiveCart(Cart paidCart, BigDecimal total) {
        float finalAmount = total.floatValue();
        paidCart.setTotal(finalAmount);
        paidCart.setPrice(finalAmount);
        paidCart.setStatus("COMPLETED");
        cartService.update(paidCart);

        Cart newActiveCart = new Cart();
        newActiveCart.setUser(paidCart.getUser());
        newActiveCart.setStatus("ACTIVE");
        newActiveCart.setDate(new Date());
        newActiveCart.setTotal(0f);
        newActiveCart.setPrice(0f);
        cartService.create(newActiveCart);
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (isBlank(baseUrl)) {
            throw new BusinessException("La configuracion del banco no es valida (base URL).");
        }
        return baseUrl.trim().replaceAll("/+$", "");
    }

    private String normalizeIban(String iban) {
        if (isBlank(iban)) {
            throw new BusinessException("La configuracion del banco no es valida (IBAN destino).");
        }

        String compact = iban.replaceAll("\\s+", "").toUpperCase();
        if (compact.length() < 15) {
            throw new BusinessException("La configuracion del banco no es valida (IBAN destino).");
        }

        return compact.replaceAll("(.{4})(?=.)", "$1 ").trim();
    }

    private String formatCardNumber(String number) {
        String digits = number.replaceAll("\\D", "");
        if (digits.length() != 16) {
            throw new BusinessException("El numero de tarjeta debe tener 16 digitos.");
        }
        return digits.replaceAll("(.{4})(?=.)", "$1 ").trim();
    }

    private int parseCvv(String cvv) {
        String digits = cvv.replaceAll("\\D", "");
        if (digits.length() != 3) {
            throw new BusinessException("El CVV debe tener 3 digitos.");
        }
        return Integer.parseInt(digits);
    }

    private String buildExpirationDate(String expirationMonthValue) {
        String value = expirationMonthValue == null ? "" : expirationMonthValue.trim();

        try {
            LocalDate expirationDate;
            if (value.matches("^\\d{4}-\\d{2}$")) {
                expirationDate = YearMonth.parse(value).atEndOfMonth();
            } else if (value.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                expirationDate = LocalDate.parse(value);
            } else {
                throw new BusinessException("La fecha de caducidad no es valida.");
            }

            if (!expirationDate.isAfter(LocalDate.now())) {
                throw new BusinessException("La tarjeta esta caducada.");
            }

            return expirationDate.toString();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("La fecha de caducidad no es valida.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

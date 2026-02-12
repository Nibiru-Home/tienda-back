package tienda_back.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.exception.BusinessException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.service.CartProductService;
import tienda_back.domain.service.CartService;
import tienda_back.domain.service.PaymentCheckoutService;
<<<<<<< Updated upstream
=======
import tienda_back.domain.service.UserOrderService;
import tienda_back.infraestructura.payment.PaymentMicroservice;
import tienda_back.infraestructura.payment.model.PagoTarjeta;
>>>>>>> Stashed changes

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class PaymentCheckoutServiceImpl implements PaymentCheckoutService {

    private final CartService cartService;
    private final CartProductService cartProductService;
<<<<<<< Updated upstream
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String bankBaseUrl;
=======
    private final UserOrderService userOrderService;
    private final PaymentMicroservice paymentMicroservice;
>>>>>>> Stashed changes
    private final String bankLogin;
    private final String bankApiToken;
    private final String destinationIban;
    private final String paymentConcept;

    @Autowired
    public PaymentCheckoutServiceImpl(
            CartService cartService,
            CartProductService cartProductService,
<<<<<<< Updated upstream
            ObjectMapper objectMapper,
            @Value("${bank.integration.base-url:http://localhost:8081}") String bankBaseUrl,
=======
            UserOrderService userOrderService,
            PaymentMicroservice paymentMicroservice,
>>>>>>> Stashed changes
            @Value("${bank.integration.login:Marta}") String bankLogin,
            @Value("${bank.integration.api-token:token1}") String bankApiToken,
            @Value("${bank.integration.destination-iban:ES33 0081 5220 0001 2345 6789}") String destinationIban,
            @Value("${bank.integration.concept:Compra Nibiru Home}") String paymentConcept) {
        this.cartService = cartService;
        this.cartProductService = cartProductService;
<<<<<<< Updated upstream
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.bankBaseUrl = normalizeBaseUrl(bankBaseUrl);
        this.bankLogin = bankLogin;
        this.bankPassword = bankPassword;
        this.destinationIban = normalizeIban(destinationIban);
        this.paymentConcept = paymentConcept;
=======
        this.userOrderService = userOrderService;
        this.paymentMicroservice = paymentMicroservice;
        this.bankLogin = requireNotBlank(bankLogin, "La configuracion del banco no es valida (login).");
        this.bankApiToken = requireNotBlank(bankApiToken, "La configuracion del banco no es valida (api token).");
        this.destinationIban = normalizeIban(destinationIban);
        this.paymentConcept = isBlank(paymentConcept) ? "Compra Nibiru Home" : paymentConcept.trim();
        this.shippingFee = BigDecimal.valueOf(Math.max(shippingFee, 0)).setScale(2, RoundingMode.HALF_UP);
        this.freeShippingThreshold = BigDecimal.valueOf(Math.max(freeShippingThreshold, 0)).setScale(2, RoundingMode.HALF_UP);
>>>>>>> Stashed changes
    }

    @Override
    public CheckoutPaymentResultDto checkout(CheckoutPaymentDto request) {
        if (request == null) {
            throw new BusinessException("Solicitud de pago invalida.");
        }

<<<<<<< Updated upstream
        BigDecimal total = calculateTotal(items);
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El total del carrito no es valido.");
        }

        String apiToken = loginAndGetApiToken();
        payWithBank(request, total, apiToken);
        clearCart(cart, items);
=======
        String userId = requireNotBlank(request.userId(), "El usuario es obligatorio.");
        String cardHolder = requireNotBlank(request.cardHolder(), "El titular de la tarjeta es obligatorio.");
        String cardNumber = formatCardNumber(requireNotBlank(request.cardNumber(), "El numero de tarjeta es obligatorio."));
        String expirationDate = buildExpirationDate(requireNotBlank(request.expirationMonth(), "La fecha de caducidad es obligatoria."));
        int cvv = parseCvv(requireNotBlank(request.cvv(), "El CVV es obligatorio."));

        Cart cart = cartService.getActiveCart(userId);
        BigDecimal total = calculateTotal(cartProductService.getByCart(cart));

        PagoTarjeta paymentRequest = new PagoTarjeta(
                new PagoTarjeta.Autorizacion(bankLogin, bankApiToken),
                new PagoTarjeta.Origen(null, cardNumber, expirationDate, cvv, cardHolder),
                new PagoTarjeta.Destino(destinationIban),
                new PagoTarjeta.Pago(total, paymentConcept));

        paymentMicroservice.payment(paymentRequest);

        registerOrder(cart, total);
        closePaidCartAndCreateNewActiveCart(cart, total);
>>>>>>> Stashed changes

        return new CheckoutPaymentResultDto("Pago completado", total);
    }

    private BigDecimal calculateTotal(List<CartProduct> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("El carrito esta vacio.");
        }

<<<<<<< Updated upstream
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

    private BigDecimal calculateTotal(List<CartProduct> items) {
        BigDecimal total = BigDecimal.ZERO;
=======
        BigDecimal subtotal = BigDecimal.ZERO;
>>>>>>> Stashed changes
        for (CartProduct item : items) {
            if (item == null || item.getProduct() == null || item.getProduct().getPrice() == null) {
                continue;
            }

            BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(Math.max(item.getQuantity(), 0));
            subtotal = subtotal.add(price.multiply(quantity));
        }

<<<<<<< Updated upstream
    private String loginAndGetApiToken() {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("login", bankLogin);
        payload.put("password", bankPassword);

        JsonNode response = sendBankRequest("/api/clients/login", payload, true);
        String apiToken = response.path("apiToken").asText("").trim();

        if (apiToken.isBlank()) {
            throw new BusinessException("No se pudo obtener el token de autorizacion del banco.");
=======
        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        if (subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El total del carrito no es valido.");
>>>>>>> Stashed changes
        }

        BigDecimal shipping = subtotal.compareTo(freeShippingThreshold) >= 0
                ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
                : shippingFee;

        return subtotal.add(shipping).setScale(2, RoundingMode.HALF_UP);
    }

    private void clearCart(Cart cart, List<CartProduct> items) {
        for (CartProduct item : items) {
            if (item != null && item.getId() != null) {
                cartProductService.deleteById(item.getId());
            }
        }

        cart.setTotal(0f);
        cart.setPrice(0f);
        cartService.update(cart);
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

    private String requireNotBlank(String value, String message) {
        if (isBlank(value)) {
            throw new BusinessException(message);
        }
        return value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

package tienda_back.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
import tienda_back.infraestructura.payment.PaymentMicroservice;
import tienda_back.infraestructura.payment.model.PagoTarjeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class PaymentCheckoutServiceImpl implements PaymentCheckoutService {

    private final CartService cartService;
    private final CartProductService cartProductService;
    private final UserOrderService userOrderService;
    private final PaymentMicroservice paymentMicroservice;
    private final String bankLogin;
    private final String bankApiToken;
    private final String destinationIban;
    private final String paymentConcept;
    private final BigDecimal shippingFee;
    private final BigDecimal freeShippingThreshold;

    @Autowired
    public PaymentCheckoutServiceImpl(
            CartService cartService,
            CartProductService cartProductService,
            UserOrderService userOrderService,
            PaymentMicroservice paymentMicroservice,
            @Value("${bank.integration.login:Marta}") String bankLogin,
            @Value("${bank.integration.api-token:token1}") String bankApiToken,
            @Value("${bank.integration.destination-iban:ES33 0081 5220 0001 2345 6789}") String destinationIban,
            @Value("${bank.integration.concept:Compra Nibiru Home}") String paymentConcept,
            @Value("${checkout.shipping-fee:6.99}") double shippingFee,
            @Value("${checkout.free-shipping-threshold:80}") double freeShippingThreshold) {
        this.cartService = cartService;
        this.cartProductService = cartProductService;
        this.userOrderService = userOrderService;
        this.paymentMicroservice = paymentMicroservice;
        this.bankLogin = requireNotBlank(bankLogin, "La configuracion del banco no es valida (login).");
        this.bankApiToken = requireNotBlank(bankApiToken, "La configuracion del banco no es valida (api token).");
        this.destinationIban = normalizeIban(destinationIban);
        this.paymentConcept = isBlank(paymentConcept) ? "Compra Nibiru Home" : paymentConcept.trim();
        this.shippingFee = BigDecimal.valueOf(Math.max(shippingFee, 0)).setScale(2, RoundingMode.HALF_UP);
        this.freeShippingThreshold = BigDecimal.valueOf(Math.max(freeShippingThreshold, 0)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public CheckoutPaymentResultDto checkout(CheckoutPaymentDto request) {
        if (request == null) {
            throw new BusinessException("Solicitud de pago invalida.");
        }

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

        return new CheckoutPaymentResultDto("Pago completado", total);
    }

    private BigDecimal calculateTotal(List<CartProduct> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("El carrito esta vacio.");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartProduct item : items) {
            if (item == null || item.getProduct() == null || item.getProduct().getPrice() == null) {
                continue;
            }

            BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(Math.max(item.getQuantity(), 0));
            subtotal = subtotal.add(price.multiply(quantity));
        }

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        if (subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El total del carrito no es valido.");
        }

        BigDecimal shipping = subtotal.compareTo(freeShippingThreshold) >= 0
                ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
                : shippingFee;

        return subtotal.add(shipping).setScale(2, RoundingMode.HALF_UP);
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

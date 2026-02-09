package tienda_back.controller.webmodel.response;

import java.math.BigDecimal;

public record CheckoutPaymentResponse(
        String message,
        BigDecimal amount) {
}

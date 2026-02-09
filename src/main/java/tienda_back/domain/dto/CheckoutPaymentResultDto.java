package tienda_back.domain.dto;

import java.math.BigDecimal;

public record CheckoutPaymentResultDto(
        String message,
        BigDecimal amount) {
}

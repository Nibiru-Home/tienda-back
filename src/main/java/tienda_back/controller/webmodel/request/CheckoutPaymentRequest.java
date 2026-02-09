package tienda_back.controller.webmodel.request;

import jakarta.validation.constraints.NotBlank;

public record CheckoutPaymentRequest(
        @NotBlank String userId,
        @NotBlank String cardNumber,
        @NotBlank String expirationMonth,
        @NotBlank String cvv,
        @NotBlank String cardHolder) {
}

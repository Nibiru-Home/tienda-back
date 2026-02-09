package tienda_back.domain.dto;

public record CheckoutPaymentDto(
        String userId,
        String cardNumber,
        String expirationMonth,
        String cvv,
        String cardHolder) {
}

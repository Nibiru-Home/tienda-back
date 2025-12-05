package tienda_back.domain.dto;

public record CartProductDto(
    Long id,
    int quantity,
    CartDto cart,
    ProductDto product) {
}

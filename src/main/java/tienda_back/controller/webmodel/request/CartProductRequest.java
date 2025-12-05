package tienda_back.controller.webmodel.request;

public record CartProductRequest(
        Long id,
        int quantity,
        CartRequest cart,
        ProductRequest product) {
}
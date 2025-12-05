package tienda_back.controller.webmodel.response;

public record CartProductResponse (
    Long id,
    int quantity,
    CartResponse cart,
    ProductResponse product
){
    
}


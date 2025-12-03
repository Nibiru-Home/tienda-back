package tienda_back.domain.dto;

import tienda_back.domain.model.cart;
import tienda_back.domain.model.product;

public record cartProductDto(
        Long id,
        int quantity,
        cart cart,
        product product
) {
}

package tienda_back.domain.dto;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.Product;

public record CartProductDto(
                Long id,
                int quantity,
                Cart cart,
                Product product) {
}

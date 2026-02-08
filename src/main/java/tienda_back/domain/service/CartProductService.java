package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.CartProduct;

public interface CartProductService {
    List<CartProduct> getAll();

    CartProduct getById(Long id);

    CartProduct create(CartProduct cartProduct);

    CartProduct create(Long cartId, Long productId, int quantity);

    CartProduct update(CartProduct cartProduct);

    void deleteById(Long id);

    List<CartProduct> getByCart(tienda_back.domain.model.Cart cart);
}

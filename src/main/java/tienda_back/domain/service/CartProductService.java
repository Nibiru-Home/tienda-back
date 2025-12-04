package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.CartProduct;

public interface CartProductService {
    List<CartProduct> getAll();
    CartProduct getById(Long id);
    CartProduct create(CartProduct cartProduct);
    CartProduct update(CartProduct cartProduct);
    void deleteById(Long id);
}

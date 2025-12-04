package tienda_back.domain.respository;

import tienda_back.domain.model.cartProduct;
import tienda_back.domain.model.cart;
import tienda_back.domain.model.product;
import java.util.List;
import java.util.Optional;

public interface CartProductRepository {
    cartProduct save(cartProduct cartProduct);
    Optional<cartProduct> findById(Long id);
    List<cartProduct> findAll();
    List<cartProduct> findByCart(cart cart);
    List<cartProduct> findByProduct(product product);
    Optional<cartProduct> findByCartAndProduct(cart cart, product product);
    void deleteById(Long id);
    boolean existsById(Long id);
}

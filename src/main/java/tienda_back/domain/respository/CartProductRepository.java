package tienda_back.domain.respository;

import tienda_back.domain.model.CartProduct;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface CartProductRepository {
    CartProduct save(CartProduct cartProduct);
    Optional<CartProduct> findById(Long id);
    List<CartProduct> findAll();
    List<CartProduct> findByCart(Cart cart);
    List<CartProduct> findByProduct(Product product);
    Optional<CartProduct> findByCartAndProduct(Cart cart, Product product);
    void deleteById(Long id);
    boolean existsById(Long id);
    CartProduct update(CartProduct cartProduct);
}

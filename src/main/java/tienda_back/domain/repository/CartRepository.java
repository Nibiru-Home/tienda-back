package tienda_back.domain.repository;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    List<Cart> findAll();
    List<Cart> findByUser(User user);
    List<Cart> findByStatus(String status);
    List<Cart> findByUserAndStatus(User user, String status);
    void deleteById(Long id);
    boolean existsById(Long id);
    Cart update(Cart cart);
}

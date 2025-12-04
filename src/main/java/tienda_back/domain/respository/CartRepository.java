package tienda_back.domain.respository;

import tienda_back.domain.model.cart;
import tienda_back.domain.model.user;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    cart save(cart cart);
    Optional<cart> findById(Long id);
    List<cart> findAll();
    List<cart> findByUser(user user);
    List<cart> findByStatus(String status);
    List<cart> findByUserAndStatus(user user, String status);
    void deleteById(Long id);
    boolean existsById(Long id);
}

package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.Cart;

public interface CartService {
    List<Cart> getAll();
    Cart getById(Long id);
    Cart create(Cart cart);
    Cart update(Cart cart);
    void deleteById(Long id);
}

package tienda_back.domain.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.repository.CartRepository;
import tienda_back.domain.service.CartService;

@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final tienda_back.domain.service.UserService userService;

    public CartServiceImpl(CartRepository cartRepository, tienda_back.domain.service.UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El carrito con el id: " + id + " no existe"));
    }

    @Override
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(Cart cart) {
        Long id = cart.getId();
        if (id == null || !cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("El carrito con el id: " + id + " no existe");
        }
        return cartRepository.update(cart);
    }

    @Override
    public void deleteById(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("El carrito con el id: " + id + " no existe");
        }
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getActiveCart(String userId) {
        tienda_back.domain.model.User user = userService.getById(java.util.UUID.fromString(userId));
        List<Cart> activeCarts = cartRepository.findByUserAndStatus(user, "ACTIVE");

        if (activeCarts.isEmpty()) {
            activeCarts = cartRepository.findByUserAndStatus(user, "ACTIVO");
        }

        if (activeCarts.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setStatus("ACTIVE");
            newCart.setDate(new java.util.Date());
            newCart.setTotal(0f);
            newCart.setPrice(0f);
            return cartRepository.save(newCart);
        }

        return activeCarts.get(0);
    }
}

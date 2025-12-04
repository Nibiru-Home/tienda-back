package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.respository.CartRepository;
import tienda_back.domain.service.CartService;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El carrito con el id: " + id + " no existe"));
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
}

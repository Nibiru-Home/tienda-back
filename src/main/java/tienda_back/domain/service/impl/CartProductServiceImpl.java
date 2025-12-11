package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.repository.CartProductRepository;
import tienda_back.domain.service.CartProductService;

public class CartProductServiceImpl implements CartProductService {
    private final CartProductRepository cartProductRepository;

    public CartProductServiceImpl(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    @Override
    public List<CartProduct> getAll() {
        return cartProductRepository.findAll();
    }

    @Override
    public CartProduct getById(Long id) {
        return cartProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El producto del carrito con el id: " + id + " no existe"));
    }

    @Override
    public CartProduct create(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProduct update(CartProduct cartProduct) {
        Long id = cartProduct.getId();
        if (id == null || !cartProductRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto del carrito con el id: " + id + " no existe");
        }
        return cartProductRepository.update(cartProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (!cartProductRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto del carrito con el id: " + id + " no existe");
        }
        cartProductRepository.deleteById(id);
    }
}

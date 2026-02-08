package tienda_back.domain.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.repository.CartProductRepository;
import tienda_back.domain.service.CartProductService;

@Transactional
public class CartProductServiceImpl implements CartProductService {
    private final CartProductRepository cartProductRepository;

    private final tienda_back.domain.repository.CartRepository cartRepository;
    private final tienda_back.domain.repository.ProductRepository productRepository;

    public CartProductServiceImpl(CartProductRepository cartProductRepository,
            tienda_back.domain.repository.CartRepository cartRepository,
            tienda_back.domain.repository.ProductRepository productRepository) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartProduct> getAll() {
        return cartProductRepository.findAll();
    }

    @Override
    public CartProduct getById(Long id) {
        return cartProductRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("El producto del carrito con el id: " + id + " no existe"));
    }

    @Override
    public CartProduct create(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProduct create(Long cartId, Long productId, int quantity) {
        tienda_back.domain.model.Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("El carrito con el id: " + cartId + " no existe"));

        tienda_back.domain.model.Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con el id: " + productId + " no existe"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);

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

    @Override
    public List<CartProduct> getByCart(tienda_back.domain.model.Cart cart) {
        return cartProductRepository.findByCart(cart);
    }
}

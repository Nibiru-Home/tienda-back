package tienda_back.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.model.Product;
import tienda_back.domain.repository.CartProductRepository;
import tienda_back.persistence.dao.jpa.CartProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;
import tienda_back.persistence.repository.mapper.CartProductMapper;

@Repository
public class CartProductRepositoryImpl implements CartProductRepository {

    private final CartProductJpaDao cartProductJpaDao;

    public CartProductRepositoryImpl(CartProductJpaDao cartProductJpaDao) {
        this.cartProductJpaDao = cartProductJpaDao;
    }

    @Override
    public CartProduct save(CartProduct cartProduct) {
        CartProductJpaEntity entity = CartProductMapper.getInstance().cartProductToCartProductJpaEntity(cartProduct);
        if (cartProduct.getId() == null) {
            return CartProductMapper.getInstance().cartProductJpaEntityToCartProduct(cartProductJpaDao.insert(entity));
        } else {
            return CartProductMapper.getInstance().cartProductJpaEntityToCartProduct(cartProductJpaDao.update(entity));
        }
    }

    @Override
    public Optional<CartProduct> findById(Long id) {
        return cartProductJpaDao.findById(id)
                .map(CartProductMapper.getInstance()::cartProductJpaEntityToCartProduct);
    }

    @Override
    public List<CartProduct> findAll() {
        return cartProductJpaDao.findAll(0, 1000).stream()
                .map(CartProductMapper.getInstance()::cartProductJpaEntityToCartProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartProduct> findByCart(Cart cart) {
        if (cart == null || cart.getId() == null) {
            return java.util.Collections.emptyList();
        }

        return findAll().stream()
                .filter(item -> item.getCart() != null && cart.getId().equals(item.getCart().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CartProduct> findByProduct(Product product) {
        if (product == null || product.getId() == null) {
            return java.util.Collections.emptyList();
        }

        return findAll().stream()
                .filter(item -> item.getProduct() != null && product.getId().equals(item.getProduct().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartProduct> findByCartAndProduct(Cart cart, Product product) {
        if (cart == null || cart.getId() == null || product == null || product.getId() == null) {
            return Optional.empty();
        }

        return findAll().stream()
                .filter(item -> item.getCart() != null && cart.getId().equals(item.getCart().getId()))
                .filter(item -> item.getProduct() != null && product.getId().equals(item.getProduct().getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        cartProductJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cartProductJpaDao.findById(id).isPresent();
    }

    @Override
    public CartProduct update(CartProduct cartProduct) {
        return save(cartProduct);
    }
}

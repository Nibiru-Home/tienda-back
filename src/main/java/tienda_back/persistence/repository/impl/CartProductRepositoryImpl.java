package tienda_back.persistence.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.model.Product;
import tienda_back.domain.respository.CartProductRepository;
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
        // CartJpaDao/Entity limitations
        return Collections.emptyList();
    }

    @Override
    public List<CartProduct> findByProduct(Product product) {
        // ProductJpaDao/Entity limitations
        return Collections.emptyList();
    }

    @Override
    public Optional<CartProduct> findByCartAndProduct(Cart cart, Product product) {
        // Limitations
        return Optional.empty();
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

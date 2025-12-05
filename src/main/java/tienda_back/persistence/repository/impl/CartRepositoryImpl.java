package tienda_back.persistence.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.User;
import tienda_back.domain.respository.CartRepository;
import tienda_back.persistence.dao.jpa.CartJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartJpaEntity;
import tienda_back.persistence.repository.mapper.CartMapper;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaDao cartJpaDao;

    public CartRepositoryImpl(CartJpaDao cartJpaDao) {
        this.cartJpaDao = cartJpaDao;
    }

    @Override
    public Cart save(Cart cart) {
        CartJpaEntity entity = CartMapper.getInstance().cartToCartJpaEntity(cart);
        if (cart.getId() == null) {
            return CartMapper.getInstance().cartJpaEntityToCart(cartJpaDao.insert(entity));
        } else {
            return CartMapper.getInstance().cartJpaEntityToCart(cartJpaDao.update(entity));
        }
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartJpaDao.findById(id).map(CartMapper.getInstance()::cartJpaEntityToCart);
    }

    @Override
    public List<Cart> findAll() {
        return cartJpaDao.findAll(0, 1000).stream()
                .map(CartMapper.getInstance()::cartJpaEntityToCart)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cart> findByUser(User user) {
        // User field not available in CartJpaEntity
        return Collections.emptyList();
    }

    @Override
    public List<Cart> findByStatus(String status) {
        // Status field not available in CartJpaEntity/Dao
        return Collections.emptyList();
    }

    @Override
    public List<Cart> findByUserAndStatus(User user, String status) {
        // User and Status fields not available
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        cartJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cartJpaDao.findById(id).isPresent();
    }

    @Override
    public Cart update(Cart cart) {
        return save(cart);
    }
}

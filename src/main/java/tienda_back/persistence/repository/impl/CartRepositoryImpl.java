package tienda_back.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Cart;
import tienda_back.domain.model.User;
import tienda_back.domain.repository.CartRepository;
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
        if (user == null || user.getId() == null) {
            return java.util.Collections.emptyList();
        }

        return findAll().stream()
                .filter(cart -> cart.getUser() != null && user.getId().equals(cart.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cart> findByStatus(String status) {
        if (status == null || status.isBlank()) {
            return java.util.Collections.emptyList();
        }

        return findAll().stream()
                .filter(cart -> cart.getStatus() != null && cart.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cart> findByUserAndStatus(User user, String status) {
        if (user == null || user.getId() == null || status == null || status.isBlank()) {
            return java.util.Collections.emptyList();
        }

        return findAll().stream()
                .filter(cart -> cart.getUser() != null && user.getId().equals(cart.getUser().getId()))
                .filter(cart -> cart.getStatus() != null && cart.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
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

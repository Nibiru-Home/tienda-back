package tienda_back.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.repository.UserOrderRepository;
import tienda_back.domain.service.UserOrderService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final tienda_back.domain.repository.CartRepository cartRepository;

    public UserOrderServiceImpl(UserOrderRepository userOrderRepository,
            tienda_back.domain.repository.CartRepository cartRepository) {
        this.userOrderRepository = userOrderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<UserOrder> getAll() {
        return userOrderRepository.findAll();
    }

    @Override
    public UserOrder getById(String id) {
        return userOrderRepository.findById(id).orElse(null);
    }

    @Override
    public UserOrder create(UserOrder userOrder) {
        if (userOrder.getId() == null) {
            userOrder.setId(UUID.randomUUID().toString());
        }
        if (userOrder.getDate() == null) {
            userOrder.setDate(new Date());
        }
        if (userOrder.getStatus() == null) {
            userOrder.setStatus("PENDING");
        }

        if (userOrder.getCart() != null && userOrder.getCart().getId() != null) {
            tienda_back.domain.model.Cart cart = cartRepository.findById(userOrder.getCart().getId()).orElse(null);
            if (cart != null) {
                userOrder.setCart(cart);

                if (userOrder.getTotal() == null) {
                    userOrder.setTotal(cart.getTotal() != null ? Double.valueOf(cart.getTotal()) : 0.0);
                }

                if (userOrder.getUser() == null) {
                    userOrder.setUser(cart.getUser());
                }
            }
        }

        return userOrderRepository.save(userOrder);
    }

    @Override
    public UserOrder update(UserOrder userOrder) {
        if (userOrderRepository.existsById(userOrder.getId())) {
            return userOrderRepository.update(userOrder);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        userOrderRepository.deleteById(id);
    }

    @Override
    public List<UserOrder> getByUser(User user) {
        return userOrderRepository.findByUser(user);
    }
}

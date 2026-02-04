package tienda_back.domain.repository;

import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository {
    UserOrder save(UserOrder userOrder);

    Optional<UserOrder> findById(String id);

    List<UserOrder> findAll();

    List<UserOrder> findByUser(User user);

    void deleteById(String id);

    boolean existsById(String id);

    UserOrder update(UserOrder userOrder);
}

package tienda_back.domain.service;

import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;

import java.util.List;

public interface UserOrderService {
    List<UserOrder> getAll();

    UserOrder getById(String id);

    UserOrder create(UserOrder userOrder);

    UserOrder update(UserOrder userOrder);

    void deleteById(String id);

    List<UserOrder> getByUser(User user);
}

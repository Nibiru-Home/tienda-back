package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.User;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User create(User user);
    User update(User user);
    void deleteById(Long id);
}

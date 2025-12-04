package tienda_back.domain.respository;

import tienda_back.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    void deleteById(Long id);
    boolean existsById(Long id);
    User update(User user);
}

package tienda_back.domain.respository;

import tienda_back.domain.model.user;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    user save(user user);
    Optional<user> findById(Long id);
    List<user> findAll();
    Optional<user> findByEmail(String email);
    void deleteById(Long id);
    boolean existsById(Long id);
}

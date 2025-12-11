package tienda_back.domain.repository;

import java.util.UUID;

import tienda_back.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    void deleteById(UUID id);
    boolean existsById(UUID id);
    User update(User user);
    boolean existsByEmail(String email);
}

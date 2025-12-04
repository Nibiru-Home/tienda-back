package tienda_back.domain.respository;

import tienda_back.domain.model.category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    category save(category category);
    Optional<category> findById(Long id);
    List<category> findAll();
    Optional<category> findByName(String name);
    void deleteById(Long id);
    boolean existsById(Long id);
}

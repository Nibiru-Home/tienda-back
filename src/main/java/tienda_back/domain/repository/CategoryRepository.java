package tienda_back.domain.repository;

import tienda_back.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    Optional<Category> findByName(String name);
    void deleteById(Long id);
    boolean existsById(Long id);
    Category update(Category category);
}

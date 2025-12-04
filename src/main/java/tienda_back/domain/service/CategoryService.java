package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.Category;

public interface CategoryService {
    List<Category> getAll();

    Category getById(Long id);

    Category create(Category category);

    Category update(Category category);

    void deleteById(Long id);
}

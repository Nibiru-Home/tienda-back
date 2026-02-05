package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Category;
import tienda_back.domain.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import tienda_back.domain.service.CategoryService;

@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoria con el id: " + id + " no existe"));
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Long id = category.getId();
        if (id == null || !categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("La categoria con el id: " + id + " no existe");
        }
        return categoryRepository.update(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("La categoria con el nombre: " + name + " no existe"));
    }
}

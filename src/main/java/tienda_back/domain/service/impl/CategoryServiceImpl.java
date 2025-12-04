package tienda_back.domain.service.impl;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Category;
import tienda_back.domain.respository.CategoryRepository;
import tienda_back.domain.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

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
        return categoryRepository.update(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoria con el id: " + id + " no existe"));
        categoryRepository.deleteById(id);
    }
}

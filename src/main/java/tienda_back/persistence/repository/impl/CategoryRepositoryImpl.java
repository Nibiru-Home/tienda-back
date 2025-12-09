package tienda_back.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Category;
import tienda_back.domain.respository.CategoryRepository;
import tienda_back.persistence.dao.jpa.CategoryJpaDao;
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;
import tienda_back.persistence.repository.mapper.CategoryMapper;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaDao categoryJpaDao;

    public CategoryRepositoryImpl(CategoryJpaDao categoryJpaDao) {
        this.categoryJpaDao = categoryJpaDao;
    }

    @Override
    public List<Category> findAll() {

        return categoryJpaDao.findAll(0, 1000).stream()
                .map(CategoryMapper.getInstance()::categoryJpaEntityToCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaDao.findById(id).map(CategoryMapper.getInstance()::categoryJpaEntityToCategory);
    }

    @Override
    public Optional<Category> findByName(String name) {

        return categoryJpaDao.findByName(name).map(CategoryMapper.getInstance()::categoryJpaEntityToCategory);
    }

    @Override
    public Category save(Category category) {
        CategoryJpaEntity entity = CategoryMapper.getInstance().categoryToCategoryJpaEntity(category);
        if (category.getId() == null) {
            return CategoryMapper.getInstance().categoryJpaEntityToCategory(categoryJpaDao.insert(entity));
        } else {
            return CategoryMapper.getInstance().categoryJpaEntityToCategory(categoryJpaDao.update(entity));
        }
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryJpaDao.findById(id).isPresent();
    }

    @Override
    public Category update(Category category) {
        return save(category);
    }
}

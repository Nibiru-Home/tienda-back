package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.Category;
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;

public class CategoryMapper {

    private static CategoryMapper INSTANCE;

    private CategoryMapper() {
    }

    public static CategoryMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapper();
        }
        return INSTANCE;
    }

    public Category categoryJpaEntityToCategory(CategoryJpaEntity categoryJpaEntity) {
        if (categoryJpaEntity == null) {
            return null;
        }

        return new Category(
                categoryJpaEntity.getId().longValue(),
                categoryJpaEntity.getName());
    }

    public CategoryJpaEntity categoryToCategoryJpaEntity(Category category) {
        if (category == null) {
            return null;
        }

        CategoryJpaEntity categoryJpaEntity = new CategoryJpaEntity();
        if (category.getId() != null) {
            categoryJpaEntity.setId(category.getId().intValue());
        }
        categoryJpaEntity.setName(category.getName());
        categoryJpaEntity.setSlug(category.getName());

        return categoryJpaEntity;
    }
}

package tienda_back.domain.mapper;

import tienda_back.domain.model.Category;
import tienda_back.domain.dto.CategoryDto;

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

    public CategoryDto categoryToCategoryDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return new Category(
                categoryDto.id(),
                categoryDto.name());
    }
}

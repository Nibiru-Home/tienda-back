package tienda_back.domain.mapper;

import tienda_back.domain.model.category;
import tienda_back.domain.dto.categoryDto;

public class CategoryMapper {
    private static CategoryMapper INSTANCE;

    private CategoryMapper() {}

    public static CategoryMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapper();
        }
        return INSTANCE;
    }

    public categoryDto categoryToCategoryDto(category category) {
        if (category == null) {
            return null;
        }

        return new categoryDto(
                category.getId(),
                category.getName()
        );
    }

    public category categoryDtoToCategory(categoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return new category(
                categoryDto.id(),
                categoryDto.name()
        );
    }
}

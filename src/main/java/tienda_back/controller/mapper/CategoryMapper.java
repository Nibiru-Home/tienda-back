package tienda_back.controller.mapper;

import tienda_back.domain.dto.CategoryDto;
import tienda_back.controller.webmodel.request.CategoryRequest;
import tienda_back.controller.webmodel.response.CategoryResponse;

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

    public CategoryDto categoryRequestToCategoryDto(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }

        return new CategoryDto(
                categoryRequest.id(),
                categoryRequest.name());
    }

    public CategoryResponse categoryDtoToCategoryResponse(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        return new CategoryResponse(
                categoryDto.id(),
                categoryDto.name());
    }
}

package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.CategoryRequest;
import tienda_back.controller.webmodel.response.CategoryResponse;
import tienda_back.domain.dto.CategoryDto;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private final CategoryMapper mapper = CategoryMapper.getInstance();

    @Test
    void testGetInstance() {
        CategoryMapper instance1 = CategoryMapper.getInstance();
        CategoryMapper instance2 = CategoryMapper.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void testCategoryRequestToCategoryDto_Success() {
        CategoryRequest request = new CategoryRequest(1L, "Electronics");

        CategoryDto result = mapper.categoryRequestToCategoryDto(request);

        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.name(), result.name());
    }

    @Test
    void testCategoryRequestToCategoryDto_NullRequest() {
        CategoryDto result = mapper.categoryRequestToCategoryDto(null);
        assertNull(result);
    }

    @Test
    void testCategoryDtoToCategoryResponse_Success() {
        CategoryDto dto = new CategoryDto(1L, "Electronics");

        CategoryResponse result = mapper.categoryDtoToCategoryResponse(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.name(), result.name());
    }

    @Test
    void testCategoryDtoToCategoryResponse_NullDto() {
        CategoryResponse result = mapper.categoryDtoToCategoryResponse(null);
        assertNull(result);
    }
}

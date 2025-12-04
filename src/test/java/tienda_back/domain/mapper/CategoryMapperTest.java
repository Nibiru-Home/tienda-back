package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.model.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        CategoryMapper instance1 = CategoryMapper.getInstance();
        CategoryMapper instance2 = CategoryMapper.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testCategoryToCategoryDto_WithNullCategory_ReturnsNull() {
        CategoryMapper mapper = CategoryMapper.getInstance();

        CategoryDto result = mapper.categoryToCategoryDto(null);

        assertNull(result, "Mapping null Category should return null");
    }

    @Test
    void testCategoryDtoToCategory_WithNullDto_ReturnsNull() {
        CategoryMapper mapper = CategoryMapper.getInstance();

        Category result = mapper.categoryDtoToCategory(null);

        assertNull(result, "Mapping null CategoryDto should return null");
    }

    @Test
    void testCategoryToCategoryDto_WithCompleteCategory_MapsAllFields() {
        CategoryMapper mapper = CategoryMapper.getInstance();
        Category category = new Category(1L, "Electronics");

        CategoryDto result = mapper.categoryToCategoryDto(category);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Electronics", result.name());
    }

    @Test
    void testCategoryDtoToCategory_WithCompleteDto_MapsAllFields() {
        CategoryMapper mapper = CategoryMapper.getInstance();
        CategoryDto categoryDto = new CategoryDto(2L, "Books");

        Category result = mapper.categoryDtoToCategory(categoryDto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Books", result.getName());
    }

    @Test
    void testBidirectionalMapping_PreservesData() {
        CategoryMapper mapper = CategoryMapper.getInstance();
        Category originalCategory = new Category(3L, "Clothing");

        CategoryDto dto = mapper.categoryToCategoryDto(originalCategory);
        Category resultCategory = mapper.categoryDtoToCategory(dto);

        assertNotNull(resultCategory);
        assertEquals(originalCategory.getId(), resultCategory.getId());
        assertEquals(originalCategory.getName(), resultCategory.getName());
    }

    @Test
    void testCategoryToCategoryDto_WithDifferentNames_MapsCorrectly() {
        CategoryMapper mapper = CategoryMapper.getInstance();
        Category category1 = new Category(4L, "Sports & Outdoors");
        Category category2 = new Category(5L, "Home & Garden");

        CategoryDto result1 = mapper.categoryToCategoryDto(category1);
        CategoryDto result2 = mapper.categoryToCategoryDto(category2);

        assertEquals("Sports & Outdoors", result1.name());
        assertEquals("Home & Garden", result2.name());
    }
}

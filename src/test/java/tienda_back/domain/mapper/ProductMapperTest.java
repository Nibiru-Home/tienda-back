package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.model.Category;
import tienda_back.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        ProductMapper instance1 = ProductMapper.getInstance();
        ProductMapper instance2 = ProductMapper.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testProductToProductDto_WithNullProduct_ReturnsNull() {
        ProductMapper mapper = ProductMapper.getInstance();

        ProductDto result = mapper.productToProductDto(null);

        assertNull(result, "Mapping null Product should return null");
    }

    @Test
    void testProductDtoToProduct_WithNullDto_ReturnsNull() {
        ProductMapper mapper = ProductMapper.getInstance();

        Product result = mapper.productDtoToProduct(null);

        assertNull(result, "Mapping null ProductDto should return null");
    }

    @Test
    void testProductToProductDto_WithCategoryList_TakesFirstCategory() {
        ProductMapper mapper = ProductMapper.getInstance();
        Category category1 = new Category(1L, "Electronics");
        Category category2 = new Category(2L, "Computers");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(999.99);
        product.setStock(10);
        product.setCategories(categories);

        ProductDto result = mapper.productToProductDto(product);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Laptop", result.name());
        assertEquals("Gaming laptop", result.description());
        assertEquals(999.99, result.price());
        assertEquals(10, result.stock());
        assertNotNull(result.category());
        assertEquals(category1.getId(), result.category().get(0).id());
        assertEquals(category1.getName(), result.category().get(0).name());
    }

    @Test
    void testProductToProductDto_WithEmptyCategoryList_MapsNullCategory() {
        ProductMapper mapper = ProductMapper.getInstance();
        Product product = new Product();
        product.setId(2L);
        product.setName("Mouse");
        product.setDescription("Wireless mouse");
        product.setPrice(29.99);
        product.setStock(50);
        product.setCategories(new ArrayList<>());

        ProductDto result = mapper.productToProductDto(product);

        assertNotNull(result);
        assertEquals(2L, result.id());
        assertNull(result.category(), "Empty category list should map to null category");
    }

    @Test
    void testProductToProductDto_WithNullCategoryList_MapsNullCategory() {
        ProductMapper mapper = ProductMapper.getInstance();
        Product product = new Product();
        product.setId(6L);
        product.setName("Monitor");
        product.setDescription("4K Monitor");
        product.setPrice(300.0);
        product.setStock(15);
        product.setCategories(null);

        ProductDto result = mapper.productToProductDto(product);

        assertNotNull(result);
        assertEquals(6L, result.id());
        assertNull(result.category(), "Null category list should map to null category");
    }

    @Test
    void testProductDtoToProduct_WithCategory_CreatesListWithOneCategory() {
        ProductMapper mapper = ProductMapper.getInstance();
        List<CategoryDto> categoryDtos = List.of(new CategoryDto(1L, "Accessory"));
        ProductDto productDto = new ProductDto(3L, "Keyboard", "Mechanical Keyboard", 150.0, 20, categoryDtos);

        Product result = mapper.productDtoToProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto.id(), result.getId());
        assertEquals(productDto.name(), result.getName());
        assertEquals(productDto.description(), result.getDescription());
        assertEquals(productDto.price(), result.getPrice());
        assertEquals(productDto.stock(), result.getStock());
        assertNotNull(result.getCategories());
        assertEquals(1, result.getCategories().size());
        assertEquals("Accessory", result.getCategories().get(0).getName());
    }

    @Test
    void testProductDtoToProduct_WithNullCategory_CreatesEmptyList() {
        ProductMapper mapper = ProductMapper.getInstance();
        ProductDto productDto = new ProductDto(5L, "Cable", "USB Cable", 9.99, 100, null);

        Product result = mapper.productDtoToProduct(productDto);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertNull(result.getCategories(), "Null category should ensure category list is null");
    }

    @Test
    void testBidirectionalMapping_WithCategory_PreservesData() {
        ProductMapper mapper = ProductMapper.getInstance();
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(categoryDto);
        ProductDto originalDto = new ProductDto(6L, "Tablet", "10-inch tablet", 299.99, 30, categories);

        Product product = mapper.productDtoToProduct(originalDto);
        ProductDto resultDto = mapper.productToProductDto(product);

        assertNotNull(resultDto);
        assertEquals(originalDto.id(), resultDto.id());
        assertEquals(originalDto.name(), resultDto.name());
        assertEquals(originalDto.description(), resultDto.description());
        assertEquals(originalDto.price(), resultDto.price());
        assertEquals(originalDto.stock(), resultDto.stock());
        assertNotNull(resultDto.category());
        assertEquals(originalDto.category().get(0).id(), resultDto.category().get(0).id());
    }
}

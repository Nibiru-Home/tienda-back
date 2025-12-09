package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.mapper.ProductMapper;
import tienda_back.controller.webmodel.request.CategoryRequest;
import tienda_back.controller.webmodel.request.ProductRequest;
import tienda_back.controller.webmodel.response.ProductResponse;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.dto.ProductDto;

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
    void testProductRequestToProductDto_WithNullRequest_ReturnsNull() {
        ProductMapper mapper = ProductMapper.getInstance();
        ProductDto result = mapper.productRequestToProductDto(null);
        assertNull(result, "Mapping null request should return null");
    }

    @Test
    void testProductDtoToProductResponse_WithNullDto_ReturnsNull() {
        ProductMapper mapper = ProductMapper.getInstance();
        ProductResponse result = mapper.productDtoToProductResponse(null);
        assertNull(result, "Mapping null dto should return null");
    }

    @Test
    void testProductRequestToProductDto_MapsCorrectly() {
        ProductMapper mapper = ProductMapper.getInstance();

        // Create CategoryRequest list
        List<CategoryRequest> categories = new ArrayList<>();
        categories.add(new CategoryRequest(1L, "Electronics"));
        categories.add(new CategoryRequest(2L, "Computing"));

        // Create ProductRequest
        ProductRequest request = new ProductRequest(
                10L,
                "Laptop",
                "High performance laptop",
                999.99,
                50,
                categories,
                new ArrayList<>());

        // Execute mapping
        ProductDto result = mapper.productRequestToProductDto(request);

        // Verify
        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.name(), result.name());
        assertEquals(request.description(), result.description());
        assertEquals(request.price(), result.price());
        assertEquals(request.stock(), result.stock());

        // Verify Categories
        assertNotNull(result.category());
        assertEquals(2, result.category().size());
        assertEquals(categories.get(0).id(), result.category().get(0).id());
        assertEquals(categories.get(0).name(), result.category().get(0).name());
        assertEquals(categories.get(1).id(), result.category().get(1).id());
    }

    @Test
    void testProductDtoToProductResponse_MapsCorrectly() {
        ProductMapper mapper = ProductMapper.getInstance();

        // Create CategoryDto list
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(new CategoryDto(3L, "Books"));

        // Create ProductDto
        ProductDto dto = new ProductDto(
                20L,
                "Novel",
                "Best selling novel",
                15.50,
                100,
                categories,
                new ArrayList<>());

        // Execute mapping
        ProductResponse result = mapper.productDtoToProductResponse(dto);

        // Verify
        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.name(), result.name());
        assertEquals(dto.description(), result.description());
        assertEquals(dto.price(), result.price());
        assertEquals(dto.stock(), result.stock());

        // Verify Categories
        assertNotNull(result.category());
        assertEquals(1, result.category().size());
        assertEquals(categories.get(0).id(), result.category().get(0).id());
        assertEquals(categories.get(0).name(), result.category().get(0).name());
    }
}

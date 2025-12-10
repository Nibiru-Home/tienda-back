package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.CategoryRequest;
import tienda_back.controller.webmodel.request.ProductRequest;
import tienda_back.controller.webmodel.response.ProductResponse;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.model.Style;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper mapper = ProductMapper.getInstance();

    @Test
    void testGetInstance() {
        ProductMapper instance1 = ProductMapper.getInstance();
        ProductMapper instance2 = ProductMapper.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void testProductRequestToProductDto_Success() {
        CategoryRequest categoryRequest = new CategoryRequest(1L, "Electronics");
        ProductRequest request = new ProductRequest(
                1L,
                "Smartphone",
                "Latest model",
                999.99,
                10,
                Collections.singletonList(categoryRequest),
                Collections.singletonList(Style.MODERNO) // Assuming Style is an enum or similar
        );

        ProductDto result = mapper.productRequestToProductDto(request);

        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.name(), result.name());
        assertEquals(request.description(), result.description());
        assertEquals(request.price(), result.price());
        assertEquals(request.stock(), result.stock());
        assertEquals(1, result.category().size());
        assertEquals(categoryRequest.id(), result.category().get(0).id());
        assertEquals(1, result.styles().size());
        assertEquals(Style.MODERNO, result.styles().get(0));
    }

    @Test
    void testProductRequestToProductDto_NullRequest() {
        ProductDto result = mapper.productRequestToProductDto(null);
        assertNull(result);
    }

    @Test
    void testProductDtoToProductResponse_Success() {
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        ProductDto dto = new ProductDto(
                1L,
                "Smartphone",
                "Latest model",
                999.99,
                10,
                Collections.singletonList(categoryDto),
                Collections.singletonList(Style.MODERNO));

        ProductResponse result = mapper.productDtoToProductResponse(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.name(), result.name());
        assertEquals(dto.description(), result.description());
        assertEquals(dto.price(), result.price());
        assertEquals(dto.stock(), result.stock());
        assertEquals(1, result.category().size());
        assertEquals(categoryDto.id(), result.category().get(0).id());
        assertEquals(1, result.styles().size());
        assertEquals(Style.MODERNO, result.styles().get(0));
    }

    @Test
    void testProductDtoToProductResponse_NullDto() {
        ProductResponse result = mapper.productDtoToProductResponse(null);
        assertNull(result);
    }
}

package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.mapper.CartProductMapper;
import tienda_back.controller.webmodel.request.CartProductRequest;
import tienda_back.controller.webmodel.request.CartRequest;
import tienda_back.controller.webmodel.request.ProductRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.CartProductResponse;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.CartProductDto;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.dto.UserDto;
import tienda_back.domain.model.RoleUser;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CartProductMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        CartProductMapper instance1 = CartProductMapper.getInstance();
        CartProductMapper instance2 = CartProductMapper.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testCartProductRequestToCartProductDto_WithNullRequest_ReturnsNull() {
        CartProductMapper mapper = CartProductMapper.getInstance();
        CartProductDto result = mapper.cartProductRequestToCartProductDto(null);
        assertNull(result, "Mapping null request should return null");
    }

    @Test
    void testCartProductDtoToCartProductResponse_WithNullDto_ReturnsNull() {
        CartProductMapper mapper = CartProductMapper.getInstance();
        CartProductResponse result = mapper.cartProductDtoToCartProductResponse(null);
        assertNull(result, "Mapping null dto should return null");
    }

    @Test
    void testCartProductRequestToCartProductDto_MapsCorrectly() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        // Create nested objects
        UserRequest userRequest = new UserRequest(1L, "John", "john@test.com", "pass", "Address", "123",
                RoleUser.CUSTOMER);
        CartRequest cartRequest = new CartRequest(10L, 100.0f, 100.0f, new Date(), "ACTIVE", userRequest);
        ProductRequest productRequest = new ProductRequest(20L, "Product", "Desc", 50.0, 10, new ArrayList<>(),new ArrayList<>());

        // Create main request
        CartProductRequest request = new CartProductRequest(1L, 5, cartRequest, productRequest);

        // Execute mapping
        CartProductDto result = mapper.cartProductRequestToCartProductDto(request);

        // Verify
        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.quantity(), result.quantity());

        // Verify Cart mapping
        assertNotNull(result.cart());
        assertEquals(cartRequest.id(), result.cart().id());
        assertEquals(cartRequest.total(), result.cart().total());

        // Verify Product mapping
        assertNotNull(result.product());
        assertEquals(productRequest.id(), result.product().id());
        assertEquals(productRequest.name(), result.product().name());
    }

    @Test
    void testCartProductDtoToCartProductResponse_MapsCorrectly() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        // Create nested objects
        UserDto userDto = new UserDto(2L, "Jane", "jane@test.com", "pass", "Address 2", "456", RoleUser.ADMIN);
        CartDto cartDto = new CartDto(30L, 200.0f, 200.0f, new Date(), "PENDING", userDto);
        ProductDto productDto = new ProductDto(40L, "Product 2", "Desc 2", 75.0, 5, new ArrayList<>(),new ArrayList<>());

        // Create main DTO
        CartProductDto dto = new CartProductDto(5L, 3, cartDto, productDto);

        // Execute mapping
        CartProductResponse result = mapper.cartProductDtoToCartProductResponse(dto);

        // Verify
        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.quantity(), result.quantity());

        // Verify Cart response mapping
        assertNotNull(result.cart());
        assertEquals(cartDto.id(), result.cart().id());
        assertEquals(cartDto.total(), result.cart().total());

        // Verify Product response mapping
        assertNotNull(result.product());
        assertEquals(productDto.id(), result.product().id());
        assertEquals(productDto.name(), result.product().name());
    }
}

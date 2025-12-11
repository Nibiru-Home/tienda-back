package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.CartProductRequest;
import tienda_back.controller.webmodel.request.CartRequest;
import tienda_back.controller.webmodel.request.ProductRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.CartProductResponse;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.CartProductDto;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.Style;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CartProductMapperTest {

    private final CartProductMapper mapper = CartProductMapper.getInstance();

    @Test
    void testGetInstance() {
        CartProductMapper instance1 = CartProductMapper.getInstance();
        CartProductMapper instance2 = CartProductMapper.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void testCartProductRequestToCartProductDto_Success() {
        UserRequest userRequest = new UserRequest("John", "john@test.com", "pass", "addr", "123");
        CartRequest cartRequest = new CartRequest(1L, 100.0f, 90.0f, new Date(), "PENDING", userRequest);
        ProductRequest productRequest = new ProductRequest(1L, "Product", "Desc", 50.0, 10, Collections.emptyList(),
                Collections.singletonList(Style.MODERNO));

        CartProductRequest request = new CartProductRequest(1L, 2, cartRequest, productRequest);

        CartProductDto result = mapper.cartProductRequestToCartProductDto(request);

        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.quantity(), result.quantity());
        assertEquals(request.cart().id(), result.cart().id());
        assertEquals(request.product().id(), result.product().id());
    }

    @Test
    void testCartProductRequestToCartProductDto_NullRequest() {
        CartProductDto result = mapper.cartProductRequestToCartProductDto(null);
        assertNull(result);
    }

    @Test
    void testCartProductDtoToCartProductResponse_Success() {
        UserRegisterDto userDto = new UserRegisterDto("John", "john@test.com", "pass", "addr", "123");
        CartDto cartDto = new CartDto(1L, 100.0f, 90.0f, new Date(), "PENDING", userDto);
        ProductDto productDto = new ProductDto(1L, "Product", "Desc", 50.0, 10, Collections.emptyList(),
                Collections.singletonList(Style.MODERNO));

        CartProductDto dto = new CartProductDto(1L, 2, cartDto, productDto);

        CartProductResponse result = mapper.cartProductDtoToCartProductResponse(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.quantity(), result.quantity());
        assertEquals(dto.cart().id(), result.cart().id());
        assertEquals(dto.product().id(), result.product().id());
    }

    @Test
    void testCartProductDtoToCartProductResponse_NullDto() {
        CartProductResponse result = mapper.cartProductDtoToCartProductResponse(null);
        assertNull(result);
    }
}

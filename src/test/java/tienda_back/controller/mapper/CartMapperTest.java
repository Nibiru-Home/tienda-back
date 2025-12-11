package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.CartRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.CartResponse;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.RoleUser;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CartMapperTest {

    private final CartMapper mapper = CartMapper.getInstance();

    @Test
    void testGetInstance() {
        CartMapper instance1 = CartMapper.getInstance();
        CartMapper instance2 = CartMapper.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void testCartRequestToCartDto_Success() {
        UserRequest userRequest = new UserRequest(1L, "John", "john@test.com", "pass", "addr", "123",
                RoleUser.CUSTOMER);
        Date date = new Date();
        CartRequest request = new CartRequest(1L, 100.0f, 90.0f, date, "PENDING", userRequest);

        CartDto result = mapper.cartRequestToCartDto(request);

        assertNotNull(result);
        assertEquals(request.id(), result.id());
        assertEquals(request.total(), result.total());
        assertEquals(request.price(), result.price());
        assertEquals(request.date(), result.date());
        assertEquals(request.status(), result.status());
        assertEquals(userRequest.id(), result.user().id());
    }

    @Test
    void testCartRequestToCartDto_NullRequest() {
        CartDto result = mapper.cartRequestToCartDto(null);
        assertNull(result);
    }

    @Test
    void testCartDtoToCartResponse_Success() {
        UserRegisterDto userDto = new UserRegisterDto(1L, "John", "john@test.com", "pass", "addr", "123", RoleUser.CUSTOMER);
        Date date = new Date();
        CartDto dto = new CartDto(1L, 100.0f, 90.0f, date, "PENDING", userDto);

        CartResponse result = mapper.cartDtoToCartResponse(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.total(), result.total());
        assertEquals(dto.price(), result.price());
        assertEquals(dto.date(), result.date());
        assertEquals(dto.status(), result.status());
        assertEquals(userDto.id(), result.user().id());
    }

    @Test
    void testCartDtoToCartResponse_NullDto() {
        CartResponse result = mapper.cartDtoToCartResponse(null);
        assertNull(result);
    }
}

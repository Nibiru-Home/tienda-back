package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.UserOrderRequest;
import tienda_back.controller.webmodel.response.UserOrderResponse;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.UserDto;
import tienda_back.domain.dto.UserOrderDto;

import java.util.Date;
import java.util.Collections;
import java.util.UUID;
import tienda_back.domain.model.RoleUser;

import static org.junit.jupiter.api.Assertions.*;

class UserOrderMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        UserOrderMapper instance1 = UserOrderMapper.getInstance();
        UserOrderMapper instance2 = UserOrderMapper.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testToDto_WithNullRequest_ReturnsNull() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        assertNull(mapper.toDto(null));
    }

    @Test
    void testToDto_WithValidRequest() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        UUID userId = UUID.randomUUID();
        Long cartId = 100L;
        String address = "123 Main St";
        UserOrderRequest request = new UserOrderRequest(userId.toString(), cartId, address);

        UserOrderDto result = mapper.toDto(request);

        assertNotNull(result);
        assertNull(result.id());
        assertNull(result.total());
        assertNull(result.date());
        assertNull(result.status());

        
        assertNotNull(result.user());
        assertEquals(userId, result.user().id());
        assertEquals(address, result.user().address());

        
        assertNotNull(result.cart());
        assertEquals(cartId, result.cart().id());
    }

    @Test
    void testToDto_WithInvalidUserIdFormat() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        Long cartId = 100L;
        String invalidUUID = "invalid-uuid-format";
        String address = "123 Main St";
        UserOrderRequest request = new UserOrderRequest(invalidUUID, cartId, address);

        UserOrderDto result = mapper.toDto(request);

        assertNotNull(result);
        assertNull(result.user());
        assertNotNull(result.cart());
        assertEquals(cartId, result.cart().id());
    }

    @Test
    void testToResponse_WithNullDto_ReturnsNull() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        assertNull(mapper.toResponse(null));
    }

    @Test
    void testToResponse_WithValidDto() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto(userId, "john.doe", "john@example.com", "Address", "123456", RoleUser.CUSTOMER);
        CartDto cartDto = new CartDto(1L, 100.0f, 50.0f, new Date(), "OPEN", null, Collections.emptyList());

        UserOrderDto dto = new UserOrderDto(
                "50",
                userDto,
                150.0,
                new Date(),
                "PENDING",
                cartDto);

        UserOrderResponse result = mapper.toResponse(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.total(), result.total());
        assertEquals(dto.date(), result.date());
        assertEquals(dto.status(), result.status());
        assertNotNull(result.user());
        assertEquals(userDto.id(), result.user().id());
        assertEquals(userDto.name(), result.user().name());
        assertEquals(cartDto, result.cart());
    }
}

package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.UserDto;
import tienda_back.domain.dto.UserOrderDto;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;

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
    void testToDto_WithNullDomain_ReturnsNull() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        assertNull(mapper.toDto(null));
    }

    @Test
    void testToDto_WithValidDomain() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("john.doe");

        Cart cart = new Cart();
        cart.setId(10L);
        cart.setStatus("OPEN");

        UserOrder domain = new UserOrder();
        domain.setId("5");
        domain.setTotal(150.0);
        domain.setDate(new Date());
        domain.setStatus("PENDING");
        domain.setUser(user);
        domain.setCart(cart);

        UserOrderDto result = mapper.toDto(domain);

        assertNotNull(result);
        assertEquals(domain.getId(), result.id());
        assertEquals(domain.getTotal(), result.total());
        assertEquals(domain.getDate(), result.date());
        assertEquals(domain.getStatus(), result.status());

        assertNotNull(result.user());
        assertEquals(userId, result.user().id());

        assertNotNull(result.cart());
        assertEquals(10L, result.cart().id());
    }

    @Test
    void testToDomain_WithNullDto_ReturnsNull() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        assertNull(mapper.toDomain(null));
    }

    @Test
    void testToDomain_WithValidDto() {
        UserOrderMapper mapper = UserOrderMapper.getInstance();
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto(userId, "john.doe", "john@example.com", "Address", "123456", RoleUser.CUSTOMER);
        CartDto cartDto = new CartDto(1L, 100.0f, 50.0f, new Date(), "OPEN", null, Collections.emptyList());

        UserOrderDto dto = new UserOrderDto(
                "50",
                userDto,
                150.0,
                new Date(),
                "COMPLETED",
                cartDto);
        UserOrder result = mapper.toDomain(dto);

        assertNotNull(result);
        assertEquals(dto.id(), result.getId());
        assertEquals(dto.total(), result.getTotal());
        assertEquals(dto.date(), result.getDate());
        assertEquals(dto.status(), result.getStatus());

        assertNotNull(result.getUser());
        assertEquals(userId, result.getUser().getId());

        assertNotNull(result.getCart());
        assertEquals(1L, result.getCart().getId());
    }
}

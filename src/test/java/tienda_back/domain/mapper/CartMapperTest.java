package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CartMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        CartMapper instance1 = CartMapper.getInstance();
        CartMapper instance2 = CartMapper.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testCartToCartDto_WithNullCart_ReturnsNull() {
        CartMapper mapper = CartMapper.getInstance();

        CartDto result = mapper.cartToCartDto(null);

        assertNull(result, "Mapping null Cart should return null");
    }

    @Test
    void testCartDtoToCart_WithNullDto_ReturnsNull() {
        CartMapper mapper = CartMapper.getInstance();

        Cart result = mapper.cartDtoToCart(null);

        assertNull(result, "Mapping null CartDto should return null");
    }

    @Test
    void testCartToCartDto_WithCompleteCart_MapsAllFields() {
        CartMapper mapper = CartMapper.getInstance();
        User user = new User(java.util.UUID.randomUUID(), "John Doe", "john@example.com", "password123",
                "123 Main St", "555-1234", RoleUser.CUSTOMER);
        Date date = new Date();

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(3.0f);
        cart.setPrice(150.50f);
        cart.setDate(date);
        cart.setStatus("PENDING");
        cart.setUser(user);

        CartDto result = mapper.cartToCartDto(cart);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(3.0f, result.total());
        assertEquals(150.50f, result.price());
        assertEquals(date, result.date());
        assertEquals("PENDING", result.status());
        assertNotNull(result.user());
        assertEquals(user.getName(), result.user().name());
    }

    @Test
    void testCartDtoToCart_WithCompleteDto_MapsAllFields() {
        CartMapper mapper = CartMapper.getInstance();
        User user = new User(java.util.UUID.randomUUID(), "Jane Smith", "jane@example.com", "pass456",
                "789 Oak Rd", "555-9012", RoleUser.ADMIN);
        UserRegisterDto userDto = new UserRegisterDto("Jane Smith", "jane@example.com", "pass456",
                "789 Oak Rd", "555-9012");
        Date date = new Date();

        CartDto cartDto = new CartDto(2L, 5.0f, 299.99f, date, "COMPLETED", userDto);

        Cart result = mapper.cartDtoToCart(cartDto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(5.0f, result.getTotal());
        assertEquals(299.99f, result.getPrice());
        assertEquals(date, result.getDate());
        assertEquals("COMPLETED", result.getStatus());
        assertNotNull(result.getUser());
    }

    @Test
    void testCartToCartDto_WithDifferentStatuses_MapsCorrectly() {
        CartMapper mapper = CartMapper.getInstance();
        User user = new User(java.util.UUID.randomUUID(), "Test User", "test@example.com", "test123",
                "Test Address", "555-0000", RoleUser.CUSTOMER);

        Cart cart1 = new Cart();
        cart1.setId(3L);
        cart1.setTotal(1.0f);
        cart1.setPrice(50.0f);
        cart1.setDate(new Date());
        cart1.setStatus("PENDING");
        cart1.setUser(user);

        Cart cart2 = new Cart();
        cart2.setId(4L);
        cart2.setTotal(2.0f);
        cart2.setPrice(100.0f);
        cart2.setDate(new Date());
        cart2.setStatus("SHIPPED");
        cart2.setUser(user);

        CartDto result1 = mapper.cartToCartDto(cart1);
        CartDto result2 = mapper.cartToCartDto(cart2);

        assertEquals("PENDING", result1.status());
        assertEquals("SHIPPED", result2.status());
    }

    @Test
    void testBidirectionalMapping_PreservesData() {
        CartMapper mapper = CartMapper.getInstance();
        User user = new User(java.util.UUID.randomUUID(), "Bidirectional User", "bi@example.com", "bipass",
                "Bi Address", "555-1111", RoleUser.CUSTOMER);
        Date date = new Date();

        Cart originalCart = new Cart();
        originalCart.setId(5L);
        originalCart.setTotal(10.0f);
        originalCart.setPrice(500.0f);
        originalCart.setDate(date);
        originalCart.setStatus("PROCESSING");
        originalCart.setUser(user);

        CartDto dto = mapper.cartToCartDto(originalCart);
        Cart resultCart = mapper.cartDtoToCart(dto);

        assertNotNull(resultCart);
        assertEquals(originalCart.getId(), resultCart.getId());
        assertEquals(originalCart.getTotal(), resultCart.getTotal());
        assertEquals(originalCart.getPrice(), resultCart.getPrice());
        assertEquals(originalCart.getDate(), resultCart.getDate());
        assertEquals(originalCart.getStatus(), resultCart.getStatus());
    }

    @Test
    void testCartToCartDto_WithZeroValues_MapsCorrectly() {
        CartMapper mapper = CartMapper.getInstance();
        User user = new User(java.util.UUID.randomUUID(), "Zero User", "zero@example.com", "zero123",
                "Zero St", "555-0000", RoleUser.CUSTOMER);

        Cart cart = new Cart();
        cart.setId(6L);
        cart.setTotal(0.0f);
        cart.setPrice(0.0f);
        cart.setDate(new Date());
        cart.setStatus("EMPTY");
        cart.setUser(user);

        CartDto result = mapper.cartToCartDto(cart);

        assertNotNull(result);
        assertEquals(0.0f, result.total());
        assertEquals(0.0f, result.price());
    }
}

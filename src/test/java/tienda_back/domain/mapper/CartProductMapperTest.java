package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.*;
import tienda_back.domain.model.*;

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
    void testCartProductToCartProductDto_WithNullCartProduct_ReturnsNull() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        CartProductDto result = mapper.cartProductToCartProductDto(null);

        assertNull(result, "Mapping null CartProduct should return null");
    }

    @Test
    void testCartProductDtoToCartProduct_WithNullDto_ReturnsNull() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        CartProduct result = mapper.cartProductDtoToCartProduct(null);

        assertNull(result, "Mapping null CartProductDto should return null");
    }

    @Test
    void testCartProductToCartProductDto_WithCompleteCartProduct_MapsAllFields() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        // Create User
        User user = new User(1L, "John Doe", "john@example.com", "password123",
                "123 Main St", "555-1234", RoleUser.CUSTOMER);

        // Create Cart
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(2.0f);
        cart.setPrice(100.0f);
        cart.setDate(new Date());
        cart.setStatus("PENDING");
        cart.setUser(user);

        // Create Product
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(999.99);
        product.setStock(true); // Reverted from boolean to int                                             
        product.setCategories(new ArrayList<>());

        // Create CartProduct
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setQuantity(2);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);

        CartProductDto result = mapper.cartProductToCartProductDto(cartProduct);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(2, result.quantity());
        assertNotNull(result.cart());
        assertEquals(cart.getId(), result.cart().id());
        assertNotNull(result.product());
        assertEquals(product.getId(), result.product().id());
    }

    @Test
    void testCartProductDtoToCartProduct_WithCompleteDto_MapsAllFields() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        // Create User
        User user = new User(2L, "Jane Smith", "jane@example.com", "pass456",
                "789 Oak Rd", "555-9012", RoleUser.ADMIN);

        // Create Cart
        Cart cart = new Cart();
        cart.setId(2L);
        cart.setTotal(3.0f);
        cart.setPrice(150.0f);
        cart.setDate(new Date());
        cart.setStatus("COMPLETED");
        cart.setUser(user);

        // Create Product
        Product product = new Product();
        product.setId(2L);
        product.setName("Mouse");
        product.setDescription("Wireless mouse");
        product.setPrice(29.99);
        product.setStock(true);
        product.setCategories(new ArrayList<>());

        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.getAddress(), user.getPhone(), user.getRole());
        CartDto cartDto = new CartDto(cart.getId(), cart.getTotal(), cart.getPrice(), cart.getDate(), cart.getStatus(),
                userDto);
        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getStock(), new ArrayList<>());
        CartProductDto cartProductDto = new CartProductDto(2L, 5, cartDto, productDto);

        CartProduct result = mapper.cartProductDtoToCartProduct(cartProductDto);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(5, result.getQuantity());
        assertNotNull(result.getCart());
        assertEquals(cart.getId(), result.getCart().getId());
        assertNotNull(result.getProduct());
        assertEquals(product.getId(), result.getProduct().getId());
    }

    @Test
    void testCartProductToCartProductDto_WithDifferentQuantities_MapsCorrectly() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        Cart cart = new Cart();
        cart.setId(3L);

        Product product = new Product();
        product.setId(3L);

        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setId(3L);
        cartProduct1.setQuantity(1);
        cartProduct1.setCart(cart);
        cartProduct1.setProduct(product);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setId(4L);
        cartProduct2.setQuantity(100);
        cartProduct2.setCart(cart);
        cartProduct2.setProduct(product);

        CartProductDto result1 = mapper.cartProductToCartProductDto(cartProduct1);
        CartProductDto result2 = mapper.cartProductToCartProductDto(cartProduct2);

        assertEquals(1, result1.quantity());
        assertEquals(100, result2.quantity());
    }

    @Test
    void testBidirectionalMapping_PreservesData() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        // Create User
        User user = new User(3L, "Test User", "test@example.com", "test123",
                "Test Address", "555-0000", RoleUser.CUSTOMER);

        // Create Cart
        Cart cart = new Cart();
        cart.setId(5L);
        cart.setTotal(1.0f);
        cart.setPrice(50.0f);
        cart.setDate(new Date());
        cart.setStatus("PENDING");
        cart.setUser(user);

        // Create Product
        Product product = new Product();
        product.setId(5L);
        product.setName("Keyboard");
        product.setDescription("Mechanical keyboard");
        product.setPrice(79.99);
        product.setStock(true); // Reverted from boolean to int
        product.setCategories(new ArrayList<>());

        // Create CartProduct
        CartProduct originalCartProduct = new CartProduct();
        originalCartProduct.setId(5L);
        originalCartProduct.setQuantity(3);
        originalCartProduct.setCart(cart);
        originalCartProduct.setProduct(product);

        CartProductDto dto = mapper.cartProductToCartProductDto(originalCartProduct);
        CartProduct resultCartProduct = mapper.cartProductDtoToCartProduct(dto);

        assertNotNull(resultCartProduct);
        assertEquals(originalCartProduct.getId(), resultCartProduct.getId());
        assertEquals(originalCartProduct.getQuantity(), resultCartProduct.getQuantity());
        assertEquals(originalCartProduct.getCart().getId(), resultCartProduct.getCart().getId());
        assertEquals(originalCartProduct.getProduct().getId(), resultCartProduct.getProduct().getId());
    }

    @Test
    void testCartProductToCartProductDto_WithZeroQuantity_MapsCorrectly() {
        CartProductMapper mapper = CartProductMapper.getInstance();

        Cart cart = new Cart();
        cart.setId(6L);

        Product product = new Product();
        product.setId(6L);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(6L);
        cartProduct.setQuantity(0);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);

        CartProductDto result = mapper.cartProductToCartProductDto(cartProduct);

        assertNotNull(result);
        assertEquals(0, result.quantity());
    }
}

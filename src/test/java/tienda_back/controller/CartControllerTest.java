package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.model.Cart;
import tienda_back.domain.service.CartService;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockitoBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId(1L);
    }

    @Nested
    class GetAllCartsTests {
        @Test
        void getAllCarts_ShouldReturnCarts() throws Exception {
            List<Cart> carts = Collections.singletonList(cart);
            when(cartService.getAll()).thenReturn(carts);

            mockMvc.perform(get("/api/carts"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").value(1));
        }
    }

    @Nested
    class GetCartByIdTests {
        @Test
        void getCartById_ShouldReturnCart_WhenExists() throws Exception {
            when(cartService.getById(1L)).thenReturn(cart);

            mockMvc.perform(get("/api/carts/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1));
        }
    }

    @Nested
    class CreateCartTests {
        @Test
        void createCart_ShouldReturnCreatedCart() throws Exception {
            CartDto inputDto = new CartDto(null, 0.0f, 0.0f, null, "PENDING", null);
            Cart createdCart = new Cart();
            createdCart.setId(1L);
            createdCart.setStatus("PENDING");

            when(cartService.create(any(Cart.class))).thenReturn(createdCart);

            mockMvc.perform(post("/api/carts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.status").value("PENDING"));
        }
    }

    @Nested
    class UpdateCartTests {
        @Test
        void updateCart_ShouldReturnUpdatedCart() throws Exception {
            Long id = 1L;
            CartDto inputDto = new CartDto(id, 100.0f, 90.0f, null, "COMPLETED", null);
            Cart updatedCart = new Cart();
            updatedCart.setId(id);
            updatedCart.setStatus("COMPLETED");

            when(cartService.update(any(Cart.class))).thenReturn(updatedCart);

            mockMvc.perform(put("/api/carts/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.status").value("COMPLETED"));
        }
    }

    @Nested
    class DeleteCartByIdTests {
        @Test
        void deleteCart_ShouldReturnNoContent() throws Exception {
            Long id = 1L;
            doNothing().when(cartService).deleteById(id);

            mockMvc.perform(delete("/api/carts/{id}", id))
                    .andExpect(status().isNoContent());
        }
    }
}

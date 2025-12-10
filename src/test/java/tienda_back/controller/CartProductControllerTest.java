package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.domain.dto.CartProductDto;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.service.CartProductService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartProductController.class)
class CartProductControllerTest {

    @MockitoBean
    private CartProductService cartProductService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CartProduct cartProduct;

    @BeforeEach
    void setUp() {
        cartProduct = new CartProduct();
        cartProduct.setId(1L);
    }

    @Nested
    class GetAllCartProductsTests {
        @Test
        void getAllCartProducts_ShouldReturnCartProducts() throws Exception {
            List<CartProduct> cartProducts = Collections.singletonList(cartProduct);
            when(cartProductService.getAll()).thenReturn(cartProducts);

            mockMvc.perform(get("/api/cart-products"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").value(1));
        }
    }

    @Nested
    class GetCartProductByIdTests {
        @Test
        void getCartProductById_ShouldReturnCartProduct_WhenExists() throws Exception {
            when(cartProductService.getById(1L)).thenReturn(cartProduct);

            mockMvc.perform(get("/api/cart-products/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1));
        }
    }

    @Nested
    class CreateCartProductTests {
        @Test
        void createCartProduct_ShouldReturnCreatedCartProduct() throws Exception {
            CartProductDto inputDto = new CartProductDto(null, 2, null, null);
            CartProduct createdCartProduct = new CartProduct();
            createdCartProduct.setId(1L);
            createdCartProduct.setQuantity(2);

            when(cartProductService.create(any(CartProduct.class))).thenReturn(createdCartProduct);

            mockMvc.perform(post("/api/cart-products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value(2));
        }
    }

    @Nested
    class UpdateCartProductTests {
        @Test
        void updateCartProduct_ShouldReturnUpdatedCartProduct() throws Exception {
            Long id = 1L;
            CartProductDto inputDto = new CartProductDto(id, 3, null, null);
            CartProduct updatedCartProduct = new CartProduct();
            updatedCartProduct.setId(id);
            updatedCartProduct.setQuantity(3);

            when(cartProductService.update(any(CartProduct.class))).thenReturn(updatedCartProduct);

            mockMvc.perform(put("/api/cart-products/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.quantity").value(3));
        }
    }

    @Nested
    class DeleteCartProductByIdTests {
        @Test
        void deleteCartProduct_ShouldReturnNoContent() throws Exception {
            Long id = 1L;
            doNothing().when(cartProductService).deleteById(id);

            mockMvc.perform(delete("/api/cart-products/{id}", id))
                    .andExpect(status().isNoContent());
        }
    }
}

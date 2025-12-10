package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.model.Product;
import tienda_back.domain.service.ProductService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockitoBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Smartphone", "Latest model", 999.99, 10, null, null);
    }

    @Nested
    class GetAllProductsTests {
        @Test
        void getAllProducts_ShouldReturnProducts() throws Exception {
            List<Product> products = Collections.singletonList(product);
            when(productService.getAll()).thenReturn(products);

            mockMvc.perform(get("/api/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Smartphone"));
        }
    }

    @Nested
    class GetProductByIdTests {
        @Test
        void getProductById_ShouldReturnProduct_WhenExists() throws Exception {
            when(productService.getById(1L)).thenReturn(product);

            mockMvc.perform(get("/api/products/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Smartphone"));
        }
    }

    @Nested
    class CreateProductTests {
        @Test
        void createProduct_ShouldReturnCreatedProduct() throws Exception {
            ProductDto inputDto = new ProductDto(null, "New Product", "Desc", 50.0, 5, Collections.emptyList(),
                    Collections.emptyList());
            Product createdProduct = new Product(1L, "New Product", "Desc", 50.0, 5, null, null);

            when(productService.create(any(Product.class))).thenReturn(createdProduct);

            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("New Product"));
        }
    }

    @Nested
    class UpdateProductTests {
        @Test
        void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
            Long id = 1L;
            ProductDto inputDto = new ProductDto(id, "Updated Product", "Desc", 60.0, 5, Collections.emptyList(),
                    Collections.emptyList());
            Product updatedProduct = new Product(id, "Updated Product", "Desc", 60.0, 5, null, null);

            when(productService.update(any(Product.class))).thenReturn(updatedProduct);

            mockMvc.perform(put("/api/products/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Updated Product"));
        }
    }

    @Nested
    class DeleteProductByIdTests {
        @Test
        void deleteProduct_ShouldReturnNoContent() throws Exception {
            Long id = 1L;
            doNothing().when(productService).deleteById(id);

            mockMvc.perform(delete("/api/products/{id}", id))
                    .andExpect(status().isNoContent());
        }
    }
}

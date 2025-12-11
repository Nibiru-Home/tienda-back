package tienda_back.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Product;
import tienda_back.domain.repository.ProductRepository;
import tienda_back.domain.service.ProductService;

public class ProductServiceImplTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Product 1");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Product 2");

        List<Product> products = Arrays.asList(p1, p2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAll();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByIdFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getById(1L);
        });

        assertEquals("El producto con el id: 1 no existe", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setName("New Product");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("New Product");

        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.create(product);

        assertEquals(savedProduct, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Product");

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void updateProductNotFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Product");

        when(productRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(product);
        });

        assertEquals("El producto con el id: 1 no existe", exception.getMessage());
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).update(any());
    }

    @Test
    void deleteProductById() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductByIdNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteById(1L);
        });

        assertEquals("El producto con el id: 1 no existe", exception.getMessage());
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(anyLong());
    }
}

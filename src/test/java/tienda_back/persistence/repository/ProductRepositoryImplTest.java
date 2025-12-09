package tienda_back.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tienda_back.domain.model.Product;
import tienda_back.persistence.dao.jpa.ProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;
import tienda_back.persistence.repository.impl.ProductRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryImplTest {

    @Mock
    private ProductJpaDao productJpaDao;

    private ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl(productJpaDao);
    }

    @Test
    void testFindAll() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(productJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(entity));

        List<Product> result = productRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    void testFindById_Found() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(productJpaDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(productJpaDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productRepository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_New() {
        Product product = new Product();
        product.setName("Laptop");
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");

        when(productJpaDao.insert(any(ProductJpaEntity.class))).thenReturn(entity);

        Product result = productRepository.save(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productJpaDao).insert(any(ProductJpaEntity.class));
    }

    @Test
    void testSave_Update() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");

        when(productJpaDao.update(any(ProductJpaEntity.class))).thenReturn(entity);

        Product result = productRepository.save(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productJpaDao).update(any(ProductJpaEntity.class));
    }

    @Test
    void testDeleteById() {
        productRepository.deleteById(1L);
        verify(productJpaDao).deleteById(1L);
    }
}

package tienda_back.persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

@ExtendWith(MockitoExtension.class)
public class ProductDaoJpaImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<ProductJpaEntity> typedQuery;

    @InjectMocks
    private ProductJpaDaoImpl productJpaDao;

    @Test
    void testFindById_Found() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(entityManager.find(ProductJpaEntity.class, 1)).thenReturn(entity);

        Optional<ProductJpaEntity> result = productJpaDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(entityManager.find(ProductJpaEntity.class, 1)).thenReturn(null);

        Optional<ProductJpaEntity> result = productJpaDao.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(entityManager.createQuery(anyString(), eq(ProductJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(entity));

        List<ProductJpaEntity> result = productJpaDao.findAll(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void testFindBySlug() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(entityManager.createQuery(anyString(), eq(ProductJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("slug"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultStream()).thenReturn(Stream.of(entity));

        Optional<ProductJpaEntity> result = productJpaDao.findBySlug("laptop");

        assertTrue(result.isPresent());
        assertEquals("laptop", result.get().getSlug());
    }

    @Test
    void testInsert() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");

        ProductJpaEntity result = productJpaDao.insert(entity);

        verify(entityManager).persist(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(entityManager.find(ProductJpaEntity.class, 1)).thenReturn(entity);
        when(entityManager.merge(entity)).thenReturn(entity);

        ProductJpaEntity result = productJpaDao.update(entity);

        verify(entityManager).flush();
        verify(entityManager).merge(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDeleteById() {
        ProductJpaEntity entity = new ProductJpaEntity(1, "Laptop", "laptop");
        when(entityManager.find(ProductJpaEntity.class, 1)).thenReturn(entity);

        productJpaDao.deleteById(1L);

        verify(entityManager).remove(entity);
    }
}

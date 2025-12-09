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
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;

@ExtendWith(MockitoExtension.class)
public class CategoryJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<CategoryJpaEntity> typedQuery;

    @InjectMocks
    private CategoryJpaDaoImpl categoryJpaDao;

    @Test
    void testFindById_Found() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(entityManager.find(CategoryJpaEntity.class, 1)).thenReturn(entity);

        Optional<CategoryJpaEntity> result = categoryJpaDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(entityManager.find(CategoryJpaEntity.class, 1)).thenReturn(null);

        Optional<CategoryJpaEntity> result = categoryJpaDao.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(entityManager.createQuery(anyString(), eq(CategoryJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(entity));

        List<CategoryJpaEntity> result = categoryJpaDao.findAll(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void testFindByName() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(entityManager.createQuery(anyString(), eq(CategoryJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("name"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultStream()).thenReturn(Stream.of(entity));

        Optional<CategoryJpaEntity> result = categoryJpaDao.findByName("Electronics");

        assertTrue(result.isPresent());
        assertEquals("Electronics", result.get().getName());
    }

    @Test
    void testInsert() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");

        CategoryJpaEntity result = categoryJpaDao.insert(entity);

        verify(entityManager).persist(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(entityManager.find(CategoryJpaEntity.class, 1)).thenReturn(entity);
        when(entityManager.merge(entity)).thenReturn(entity);

        CategoryJpaEntity result = categoryJpaDao.update(entity);

        verify(entityManager).flush();
        verify(entityManager).merge(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDeleteById() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(entityManager.find(CategoryJpaEntity.class, 1)).thenReturn(entity);

        categoryJpaDao.deleteById(1L);

        verify(entityManager).remove(entity);
    }
}

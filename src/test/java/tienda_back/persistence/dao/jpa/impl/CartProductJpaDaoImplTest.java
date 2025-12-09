package tienda_back.persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;

@ExtendWith(MockitoExtension.class)
public class CartProductJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<CartProductJpaEntity> typedQuery;

    @InjectMocks
    private CartProductJpaDaoImpl cartProductJpaDao;

    @Test
    void testFindById_Found() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(entityManager.find(CartProductJpaEntity.class, 1)).thenReturn(entity);

        Optional<CartProductJpaEntity> result = cartProductJpaDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(entityManager.find(CartProductJpaEntity.class, 1)).thenReturn(null);

        Optional<CartProductJpaEntity> result = cartProductJpaDao.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(entityManager.createQuery(anyString(), eq(CartProductJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(entity));

        List<CartProductJpaEntity> result = cartProductJpaDao.findAll(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void testInsert() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);

        CartProductJpaEntity result = cartProductJpaDao.insert(entity);

        verify(entityManager).persist(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(entityManager.find(CartProductJpaEntity.class, 1)).thenReturn(entity);
        when(entityManager.merge(entity)).thenReturn(entity);

        CartProductJpaEntity result = cartProductJpaDao.update(entity);

        verify(entityManager).flush();
        verify(entityManager).merge(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDeleteById() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(entityManager.find(CartProductJpaEntity.class, 1)).thenReturn(entity);

        cartProductJpaDao.deleteById(1L);

        verify(entityManager).remove(entity);
    }
}
package tienda_back.persistence.dao.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<UserOrderJpaEntity> typedQuery;

    @InjectMocks
    private UserOrderJpaDaoImpl userOrderJpaDao;

    private UserOrderJpaEntity userOrderJpaEntity;
    private UserJpaEntity userJpaEntity;

    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(java.util.UUID.randomUUID());
        userJpaEntity.setName("John");

        userOrderJpaEntity = new UserOrderJpaEntity();
        userOrderJpaEntity.setId("order123");
        userOrderJpaEntity.setUser(userJpaEntity);
        userOrderJpaEntity.setTotal(100.0);
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        when(entityManager.createQuery("SELECT u FROM UserOrderJpaEntity u ORDER BY u.date DESC",
                UserOrderJpaEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(userOrderJpaEntity));

        List<UserOrderJpaEntity> result = userOrderJpaDao.findAll(1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("order123", result.get(0).getId());
    }

    @Test
    void findById_ShouldReturnOrder_WhenExists() {
        when(entityManager.find(UserOrderJpaEntity.class, "order123")).thenReturn(userOrderJpaEntity);

        Optional<UserOrderJpaEntity> result = userOrderJpaDao.findById("order123");

        assertTrue(result.isPresent());
        assertEquals("order123", result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(entityManager.find(UserOrderJpaEntity.class, "nonExistent")).thenReturn(null);

        Optional<UserOrderJpaEntity> result = userOrderJpaDao.findById("nonExistent");

        assertFalse(result.isPresent());
    }

    @Test
    void insert_ShouldPersistAndReturnEntity() {
        doNothing().when(entityManager).persist(any(UserOrderJpaEntity.class));

        UserOrderJpaEntity result = userOrderJpaDao.insert(userOrderJpaEntity);

        assertNotNull(result);
        assertEquals("order123", result.getId());
        verify(entityManager).persist(any(UserOrderJpaEntity.class));
    }

    @Test
    void update_ShouldMergeAndReturnEntity() {
        when(entityManager.merge(any(UserOrderJpaEntity.class))).thenReturn(userOrderJpaEntity);

        UserOrderJpaEntity result = userOrderJpaDao.update(userOrderJpaEntity);

        assertNotNull(result);
        assertEquals("order123", result.getId());
        verify(entityManager).merge(any(UserOrderJpaEntity.class));
    }

    @Test
    void deleteById_ShouldRemoveEntity_WhenExists() {
        when(entityManager.find(UserOrderJpaEntity.class, "order123")).thenReturn(userOrderJpaEntity);
        doNothing().when(entityManager).remove(any(UserOrderJpaEntity.class));

        userOrderJpaDao.deleteById("order123");

        verify(entityManager).remove(any(UserOrderJpaEntity.class));
    }

    @Test
    void deleteById_ShouldNotRemoveEntity_WhenNotExists() {
        when(entityManager.find(UserOrderJpaEntity.class, "nonExistent")).thenReturn(null);

        userOrderJpaDao.deleteById("nonExistent");

        verify(entityManager, never()).remove(any());
    }

    @Test
    void findByUser_ShouldReturnFilterOrders() {
        when(entityManager.createQuery("SELECT u FROM UserOrderJpaEntity u WHERE u.user = :user",
                UserOrderJpaEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("user", userJpaEntity)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(userOrderJpaEntity));

        List<UserOrderJpaEntity> result = userOrderJpaDao.findByUser(userJpaEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("order123", result.get(0).getId());
    }
}

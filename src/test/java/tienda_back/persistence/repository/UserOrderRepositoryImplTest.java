package tienda_back.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;
import tienda_back.persistence.dao.jpa.UserOrderJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;
import tienda_back.persistence.repository.impl.UserOrderRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserOrderRepositoryImplTest {

    @Mock
    private UserOrderJpaDao userOrderJpaDao;

    @InjectMocks
    private UserOrderRepositoryImpl userOrderRepository;

    private UserOrder userOrder;
    private UserOrderJpaEntity userOrderJpaEntity;
    private User user;

    @BeforeEach
    void setUp() {
        java.util.UUID sharedId = java.util.UUID.randomUUID();

        user = new User();
        user.setId(sharedId);
        user.setName("John");

        userOrder = new UserOrder();
        userOrder.setId("order123");
        userOrder.setUser(user);
        userOrder.setTotal(100.0);

        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(sharedId);
        userJpaEntity.setName("John");

        userOrderJpaEntity = new UserOrderJpaEntity();
        userOrderJpaEntity.setId("order123");
        userOrderJpaEntity.setUser(userJpaEntity);
        userOrderJpaEntity.setTotal(100.0);
    }

    @Test
    void save_ShouldReturnSavedOrder() {
        when(userOrderJpaDao.insert(any(UserOrderJpaEntity.class))).thenReturn(userOrderJpaEntity);

        UserOrder result = userOrderRepository.save(userOrder);

        assertNotNull(result);
        assertEquals("order123", result.getId());
        assertEquals(100.0, result.getTotal());
        verify(userOrderJpaDao).insert(any(UserOrderJpaEntity.class));
    }

    @Test
    void findById_ShouldReturnOrder_WhenExists() {
        when(userOrderJpaDao.findById("order123")).thenReturn(Optional.of(userOrderJpaEntity));

        Optional<UserOrder> result = userOrderRepository.findById("order123");

        assertTrue(result.isPresent());
        assertEquals("order123", result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(userOrderJpaDao.findById("nonExistent")).thenReturn(Optional.empty());

        Optional<UserOrder> result = userOrderRepository.findById("nonExistent");

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnOrderList() {
        when(userOrderJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(userOrderJpaEntity));

        List<UserOrder> result = userOrderRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("order123", result.get(0).getId());
    }

    @Test
    void findByUser_ShouldReturnFilterOrders() {
        when(userOrderJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(userOrderJpaEntity));

        List<UserOrder> result = userOrderRepository.findByUser(user);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("order123", result.get(0).getId());
    }

    @Test
    void deleteById_ShouldCallDaoDelete() {
        doNothing().when(userOrderJpaDao).deleteById("order123");

        userOrderRepository.deleteById("order123");

        verify(userOrderJpaDao).deleteById("order123");
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(userOrderJpaDao.findById("order123")).thenReturn(Optional.of(userOrderJpaEntity));

        boolean exists = userOrderRepository.existsById("order123");

        assertTrue(exists);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenNotExists() {
        when(userOrderJpaDao.findById("nonExistent")).thenReturn(Optional.empty());

        boolean exists = userOrderRepository.existsById("nonExistent");

        assertFalse(exists);
    }

    @Test
    void update_ShouldReturnUpdatedOrder() {
        when(userOrderJpaDao.update(any(UserOrderJpaEntity.class))).thenReturn(userOrderJpaEntity);

        UserOrder result = userOrderRepository.update(userOrder);

        assertNotNull(result);
        assertEquals("order123", result.getId());
        verify(userOrderJpaDao).update(any(UserOrderJpaEntity.class));
    }
}

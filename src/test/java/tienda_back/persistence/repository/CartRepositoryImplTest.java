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

import tienda_back.domain.model.Cart;
import tienda_back.persistence.dao.jpa.CartJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartJpaEntity;
import tienda_back.persistence.repository.impl.CartRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryImplTest {

    @Mock
    private CartJpaDao cartJpaDao;

    private CartRepositoryImpl cartRepository;

    @BeforeEach
    void setUp() {
        cartRepository = new CartRepositoryImpl(cartJpaDao);
    }

    @Test
    void testSave_New() {
        Cart cart = new Cart();
        CartJpaEntity entity = new CartJpaEntity(1);

        when(cartJpaDao.insert(any(CartJpaEntity.class))).thenReturn(entity);

        Cart result = cartRepository.save(cart);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cartJpaDao).insert(any(CartJpaEntity.class));
    }

    @Test
    void testSave_Update() {
        Cart cart = new Cart();
        cart.setId(1L);
        CartJpaEntity entity = new CartJpaEntity(1);

        when(cartJpaDao.update(any(CartJpaEntity.class))).thenReturn(entity);

        Cart result = cartRepository.save(cart);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cartJpaDao).update(any(CartJpaEntity.class));
    }

    @Test
    void testFindById_Found() {
        CartJpaEntity entity = new CartJpaEntity(1);
        when(cartJpaDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Cart> result = cartRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(cartJpaDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Cart> result = cartRepository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        CartJpaEntity entity = new CartJpaEntity(1);
        when(cartJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(entity));

        List<Cart> result = cartRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testDeleteById() {
        cartRepository.deleteById(1L);
        verify(cartJpaDao).deleteById(1L);
    }
}
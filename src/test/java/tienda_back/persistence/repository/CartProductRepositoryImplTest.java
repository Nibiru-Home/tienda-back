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

import tienda_back.domain.model.CartProduct;
import tienda_back.persistence.dao.jpa.CartProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;
import tienda_back.persistence.repository.impl.CartProductRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class CartProductRepositoryImplTest {

    @Mock
    private CartProductJpaDao cartProductJpaDao;

    private CartProductRepositoryImpl cartProductRepository;

    @BeforeEach
    void setUp() {
        cartProductRepository = new CartProductRepositoryImpl(cartProductJpaDao);
    }

    @Test
    void testSave_New() {
        CartProduct cartProduct = new CartProduct();
        CartProductJpaEntity entity = new CartProductJpaEntity(1);

        when(cartProductJpaDao.insert(any(CartProductJpaEntity.class))).thenReturn(entity);

        CartProduct result = cartProductRepository.save(cartProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cartProductJpaDao).insert(any(CartProductJpaEntity.class));
    }

    @Test
    void testSave_Update() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        CartProductJpaEntity entity = new CartProductJpaEntity(1);

        when(cartProductJpaDao.update(any(CartProductJpaEntity.class))).thenReturn(entity);

        CartProduct result = cartProductRepository.save(cartProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cartProductJpaDao).update(any(CartProductJpaEntity.class));
    }

    @Test
    void testFindById_Found() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(cartProductJpaDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<CartProduct> result = cartProductRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(cartProductJpaDao.findById(1L)).thenReturn(Optional.empty());

        Optional<CartProduct> result = cartProductRepository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        CartProductJpaEntity entity = new CartProductJpaEntity(1);
        when(cartProductJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(entity));

        List<CartProduct> result = cartProductRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testDeleteById() {
        cartProductRepository.deleteById(1L);
        verify(cartProductJpaDao).deleteById(1L);
    }
}

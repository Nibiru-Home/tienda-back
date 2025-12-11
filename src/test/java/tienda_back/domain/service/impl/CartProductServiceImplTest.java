package tienda_back.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.repository.CartProductRepository;
import tienda_back.domain.service.CartProductService;

public class CartProductServiceImplTest {
    private CartProductRepository cartProductRepository;
    private CartProductService cartProductService;

    @BeforeEach
    void setUp() {
        cartProductRepository = mock(CartProductRepository.class);
        cartProductService = new CartProductServiceImpl(cartProductRepository);
    }

    @Test
    void getAllCartProducts() {
        CartProduct cp1 = new CartProduct();
        cp1.setId(1L);
        cp1.setQuantity(1);

        CartProduct cp2 = new CartProduct();
        cp2.setId(2L);
        cp2.setQuantity(2);

        List<CartProduct> cartProducts = Arrays.asList(cp1, cp2);
        when(cartProductRepository.findAll()).thenReturn(cartProducts);

        List<CartProduct> result = cartProductService.getAll();

        assertEquals(2, result.size());
        verify(cartProductRepository, times(1)).findAll();
    }

    @Test
    void getCartProductByIdFound() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setQuantity(1);

        when(cartProductRepository.findById(1L)).thenReturn(Optional.of(cartProduct));

        CartProduct result = cartProductService.getById(1L);

        assertEquals(cartProduct, result);
        verify(cartProductRepository, times(1)).findById(1L);
    }

    @Test
    void getCartProductByIdNotFound() {
        when(cartProductRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartProductService.getById(1L);
        });

        assertEquals("El producto del carrito con el id: 1 no existe", exception.getMessage());
        verify(cartProductRepository, times(1)).findById(1L);
    }

    @Test
    void createCartProduct() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setQuantity(1);

        CartProduct savedCartProduct = new CartProduct();
        savedCartProduct.setId(1L);
        savedCartProduct.setQuantity(1);

        when(cartProductRepository.save(cartProduct)).thenReturn(savedCartProduct);

        CartProduct result = cartProductService.create(cartProduct);

        assertEquals(savedCartProduct, result);
        verify(cartProductRepository, times(1)).save(cartProduct);
    }

    @Test
    void updateCartProduct() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setQuantity(5);

        when(cartProductRepository.existsById(1L)).thenReturn(true);
        when(cartProductRepository.update(cartProduct)).thenReturn(cartProduct);

        CartProduct result = cartProductService.update(cartProduct);

        assertEquals(cartProduct, result);
        verify(cartProductRepository, times(1)).existsById(1L);
        verify(cartProductRepository, times(1)).update(cartProduct);
    }

    @Test
    void updateCartProductNotFound() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setQuantity(5);

        when(cartProductRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartProductService.update(cartProduct);
        });

        assertEquals("El producto del carrito con el id: 1 no existe", exception.getMessage());
        verify(cartProductRepository, times(1)).existsById(1L);
        verify(cartProductRepository, never()).update(any());
    }

    @Test
    void deleteCartProductById() {
        when(cartProductRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cartProductRepository).deleteById(1L);

        cartProductService.deleteById(1L);

        verify(cartProductRepository, times(1)).existsById(1L);
        verify(cartProductRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCartProductByIdNotFound() {
        when(cartProductRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartProductService.deleteById(1L);
        });

        assertEquals("El producto del carrito con el id: 1 no existe", exception.getMessage());
        verify(cartProductRepository, times(1)).existsById(1L);
        verify(cartProductRepository, never()).deleteById(anyLong());
    }
}

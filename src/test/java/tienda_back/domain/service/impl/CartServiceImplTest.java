package tienda_back.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.respository.CartRepository;
import tienda_back.domain.service.CartService;

public class CartServiceImplTest {
    private CartRepository cartRepository;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        cartService = new CartServiceImpl(cartRepository);
    }

    @Test
    void getAllCarts() {
        Cart c1 = new Cart();
        c1.setId(1L);
        c1.setStatus("ACTIVE");

        Cart c2 = new Cart();
        c2.setId(2L);
        c2.setStatus("COMPLETED");

        List<Cart> carts = Arrays.asList(c1, c2);
        when(cartRepository.findAll()).thenReturn(carts);

        List<Cart> result = cartService.getAll();

        assertEquals(2, result.size());
        verify(cartRepository, times(1)).findAll();
    }

    @Test
    void getCartByIdFound() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus("ACTIVE");

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.getById(1L);

        assertEquals(cart, result);
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void getCartByIdNotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getById(1L);
        });

        assertEquals("El carrito con el id: 1 no existe", exception.getMessage());
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void createCart() {
        Cart cart = new Cart();
        cart.setStatus("NEW");

        Cart savedCart = new Cart();
        savedCart.setId(1L);
        savedCart.setStatus("NEW");

        when(cartRepository.save(cart)).thenReturn(savedCart);

        Cart result = cartService.create(cart);

        assertEquals(savedCart, result);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void updateCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus("UPDATED");

        when(cartRepository.existsById(1L)).thenReturn(true);
        when(cartRepository.update(cart)).thenReturn(cart);

        Cart result = cartService.update(cart);

        assertEquals(cart, result);
        verify(cartRepository, times(1)).existsById(1L);
        verify(cartRepository, times(1)).update(cart);
    }

    @Test
    void updateCartNotFound() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus("UPDATED");

        when(cartRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.update(cart);
        });

        assertEquals("El carrito con el id: 1 no existe", exception.getMessage());
        verify(cartRepository, times(1)).existsById(1L);
        verify(cartRepository, never()).update(any());
    }

    @Test
    void deleteCartById() {
        when(cartRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cartRepository).deleteById(1L);

        cartService.deleteById(1L);

        verify(cartRepository, times(1)).existsById(1L);
        verify(cartRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCartByIdNotFound() {
        when(cartRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.deleteById(1L);
        });

        assertEquals("El carrito con el id: 1 no existe", exception.getMessage());
        verify(cartRepository, times(1)).existsById(1L);
        verify(cartRepository, never()).deleteById(anyLong());
    }
}

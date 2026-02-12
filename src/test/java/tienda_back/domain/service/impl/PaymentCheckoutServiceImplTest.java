package tienda_back.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.exception.BusinessException;
import tienda_back.domain.model.Cart;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.model.Product;
import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.service.CartProductService;
import tienda_back.domain.service.CartService;
import tienda_back.domain.service.UserOrderService;
import tienda_back.infraestructura.payment.PaymentMicroservice;
import tienda_back.infraestructura.payment.model.PagoTarjeta;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentCheckoutServiceImplTest {

    private CartService cartService;
    private CartProductService cartProductService;
    private UserOrderService userOrderService;
    private PaymentMicroservice paymentMicroservice;
    private PaymentCheckoutServiceImpl service;
    private Cart activeCart;

    @BeforeEach
    void setUp() {
        cartService = mock(CartService.class);
        cartProductService = mock(CartProductService.class);
        userOrderService = mock(UserOrderService.class);
        paymentMicroservice = mock(PaymentMicroservice.class);

        service = new PaymentCheckoutServiceImpl(
                cartService,
                cartProductService,
                userOrderService,
                paymentMicroservice,
                "Marta",
                "token1",
                "ES33 0081 5220 0001 2345 6789",
                "Compra Nibiru Home",
                6.99,
                80);

        activeCart = new Cart();
        activeCart.setId(1L);
        activeCart.setStatus("ACTIVE");

        User user = new User();
        user.setId(UUID.randomUUID());
        activeCart.setUser(user);
    }

    @Test
    void checkoutShouldCompletePaymentCreateOrderAndRotateCart() {
        when(cartService.getActiveCart("22222222-2222-2222-2222-222222222222")).thenReturn(activeCart);
        when(cartProductService.getByCart(activeCart)).thenReturn(List.of(buildItem(10.00, 2)));

        CheckoutPaymentDto request = new CheckoutPaymentDto(
                "22222222-2222-2222-2222-222222222222",
                "4111 1111 1111 1111",
                "2030-12",
                "321",
                "Marta Martinez");

        CheckoutPaymentResultDto result = service.checkout(request);

        assertEquals("Pago completado", result.message());
        assertEquals("26.99", result.amount().toPlainString());

        ArgumentCaptor<PagoTarjeta> paymentCaptor = ArgumentCaptor.forClass(PagoTarjeta.class);
        verify(paymentMicroservice, times(1)).payment(paymentCaptor.capture());
        PagoTarjeta payload = paymentCaptor.getValue();
        assertEquals("Marta", payload.autorizacion().login());
        assertEquals("token1", payload.autorizacion().apiToken());
        assertEquals("4111 1111 1111 1111", payload.origen().number());
        assertEquals("2030-12-31", payload.origen().expirationDate());
        assertEquals(321, payload.origen().cvv());
        assertEquals("Marta Martinez", payload.origen().name());
        assertEquals("ES33 0081 5220 0001 2345 6789", payload.destino().iban());
        assertEquals("26.99", payload.pago().importe().toPlainString());
        assertEquals("Compra Nibiru Home", payload.pago().concepto());

        verify(userOrderService, times(1)).create(any(UserOrder.class));
        verify(cartService, times(1)).update(activeCart);
        verify(cartService, times(1)).create(any(Cart.class));
    }

    @Test
    void checkoutShouldPropagateBankPaymentError() {
        when(cartService.getActiveCart("22222222-2222-2222-2222-222222222222")).thenReturn(activeCart);
        when(cartProductService.getByCart(activeCart)).thenReturn(List.of(buildItem(10.00, 1)));
        doThrow(new BusinessException("Token API invalido"))
                .when(paymentMicroservice)
                .payment(any(PagoTarjeta.class));

        CheckoutPaymentDto request = new CheckoutPaymentDto(
                "22222222-2222-2222-2222-222222222222",
                "4111 1111 1111 1111",
                "2030-12",
                "321",
                "Marta Martinez");

        BusinessException exception = assertThrows(BusinessException.class, () -> service.checkout(request));

        assertEquals("Token API invalido", exception.getMessage());
        verify(userOrderService, never()).create(any(UserOrder.class));
        verify(cartService, never()).update(any(Cart.class));
        verify(cartService, never()).create(any(Cart.class));
    }

    @Test
    void checkoutShouldFailWhenCartIsEmpty() {
        when(cartService.getActiveCart("22222222-2222-2222-2222-222222222222")).thenReturn(activeCart);
        when(cartProductService.getByCart(activeCart)).thenReturn(List.of());

        CheckoutPaymentDto request = new CheckoutPaymentDto(
                "22222222-2222-2222-2222-222222222222",
                "4111 1111 1111 1111",
                "2030-12",
                "321",
                "Marta Martinez");

        BusinessException exception = assertThrows(BusinessException.class, () -> service.checkout(request));

        assertEquals("El carrito esta vacio.", exception.getMessage());
        verify(paymentMicroservice, never()).payment(any(PagoTarjeta.class));
    }

    @Test
    void checkoutShouldFailWhenApiTokenConfigIsBlank() {
        BusinessException exception = assertThrows(BusinessException.class, () -> new PaymentCheckoutServiceImpl(
                cartService,
                cartProductService,
                userOrderService,
                paymentMicroservice,
                "Marta",
                " ",
                "ES33 0081 5220 0001 2345 6789",
                "Compra Nibiru Home",
                6.99,
                80));
        assertEquals("La configuracion del banco no es valida (api token).", exception.getMessage());
    }

    private CartProduct buildItem(double price, int quantity) {
        Product product = new Product();
        product.setPrice(price);

        CartProduct item = new CartProduct();
        item.setCart(activeCart);
        item.setProduct(product);
        item.setQuantity(quantity);
        return item;
    }
}

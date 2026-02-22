package tienda_back.infraestructura.payment.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tienda_back.domain.exception.BusinessException;
import tienda_back.infraestructura.payment.model.PagoTarjeta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentMicroserviceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private PaymentMicroserviceImpl paymentMicroservice;

    private PagoTarjeta request;

    @BeforeEach
    void setUp() {
        paymentMicroservice = new PaymentMicroserviceImpl(restTemplate, "http://localhost:8081/");
        request = new PagoTarjeta(
                new PagoTarjeta.Autorizacion("login", "token"),
                new PagoTarjeta.Origen(1L, "1234", "12/25", 123, "Name"),
                new PagoTarjeta.Destino("IBAN"),
                new PagoTarjeta.Pago(java.math.BigDecimal.valueOf(100), "Concept"));
    }

    @Test
    void normalizeBaseUrl_ShouldThrowException_WhenUrlIsInvalid() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new PaymentMicroserviceImpl(restTemplate, " ");
        });

        assertEquals("La configuracion del banco no es valida (base URL).", exception.getMessage());

        Exception nullException = assertThrows(BusinessException.class, () -> {
            new PaymentMicroserviceImpl(restTemplate, null);
        });

        assertEquals("La configuracion del banco no es valida (base URL).", nullException.getMessage());
    }

    @Test
    void payment_ShouldCallRestTemplateSuccessfully() {
        when(restTemplate.postForEntity(eq("http://localhost:8081/api/pagoTarjeta"), any(PagoTarjeta.class),
                eq(Void.class)))
                .thenReturn(ResponseEntity.ok().build());

        assertDoesNotThrow(() -> paymentMicroservice.payment(request));

        verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8081/api/pagoTarjeta"),
                any(PagoTarjeta.class), eq(Void.class));
    }

    @Test
    void payment_ShouldThrowBusinessException_WhenRestClientExceptionOccurs() {
        doThrow(new RestClientException("Connection refused"))
                .when(restTemplate)
                .postForEntity(eq("http://localhost:8081/api/pagoTarjeta"), any(PagoTarjeta.class), eq(Void.class));

        BusinessException exception = assertThrows(BusinessException.class, () -> paymentMicroservice.payment(request));

        assertTrue(exception.getMessage().contains("No se pudo conectar con el banco"));
        assertTrue(exception.getMessage().contains("Connection refused"));
    }
}

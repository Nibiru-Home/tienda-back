package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.config.filter.AuthFilter;
import tienda_back.config.filter.UserFilter;
import tienda_back.controller.webmodel.request.CheckoutPaymentRequest;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.service.PaymentCheckoutService;
import tienda_back.domain.service.TokenService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@Import({ UserFilter.class, AuthFilter.class })
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentCheckoutService paymentCheckoutService;

    @MockitoBean
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkout_shouldReturnOk_whenTokenIsValid() throws Exception {
        when(tokenService.validate(anyString())).thenReturn(true);
        when(tokenService.extractUserId(anyString())).thenReturn(UUID.randomUUID());
        when(paymentCheckoutService.checkout(any(CheckoutPaymentDto.class)))
                .thenReturn(new CheckoutPaymentResultDto("Success", BigDecimal.TEN));

        CheckoutPaymentRequest request = new CheckoutPaymentRequest("user1", "1234", "12/25", "123", "Holder");

        mockMvc.perform(post("/api/payments/checkout")
                .header("Authorization", "Bearer valid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void checkout_shouldReturnUnauthorized_whenTokenIsInvalid() throws Exception {
        when(tokenService.validate(anyString())).thenReturn(false);

        CheckoutPaymentRequest request = new CheckoutPaymentRequest("user1", "1234", "12/25", "123", "Holder");

        mockMvc.perform(post("/api/payments/checkout")
                .header("Authorization", "Bearer invalid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void checkout_shouldReturnUnauthorized_whenTokenIsMissing() throws Exception {
        CheckoutPaymentRequest request = new CheckoutPaymentRequest("user1", "1234", "12/25", "123", "Holder");

        mockMvc.perform(post("/api/payments/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}

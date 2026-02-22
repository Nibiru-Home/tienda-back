package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tienda_back.controller.webmodel.request.CheckoutPaymentRequest;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.service.PaymentCheckoutService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @MockitoBean
    private PaymentCheckoutService paymentCheckoutService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class CheckoutTests {
        @Test
        void checkout_ShouldReturnSuccessfulResponse() throws Exception {
            CheckoutPaymentRequest request = new CheckoutPaymentRequest(
                    "user123",
                    "1234567890123456",
                    "12/25",
                    "123",
                    "John Doe");

            CheckoutPaymentResultDto resultDto = new CheckoutPaymentResultDto("Payment successful",
                    java.math.BigDecimal.valueOf(150.0));
            UUID userId = UUID.randomUUID();

            when(paymentCheckoutService.checkout(any(CheckoutPaymentDto.class))).thenReturn(resultDto);

            mockMvc.perform(post("/api/payments/checkout")
                    .requestAttr("USER_ID", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Payment successful"))
                    .andExpect(jsonPath("$.amount").value(150.0));
        }

        @Test
        void checkout_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
            
            CheckoutPaymentRequest request = new CheckoutPaymentRequest(
                    null,
                    null,
                    null,
                    null,
                    null);

            UUID userId = UUID.randomUUID();

            mockMvc.perform(post("/api/payments/checkout")
                    .requestAttr("USER_ID", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }
}

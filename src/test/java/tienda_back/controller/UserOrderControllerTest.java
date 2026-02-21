package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.controller.webmodel.request.UserOrderRequest;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.service.UserOrderService;
import tienda_back.domain.service.UserService;
import tienda_back.domain.service.CartProductService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserOrderController.class)
class UserOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserOrderService userOrderService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CartProductService cartProductService;

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        String userId = UUID.randomUUID().toString();
        UserOrderRequest request = new UserOrderRequest(userId, 1L, "123 Main St");

        UserOrder createdOrder = new UserOrder();
        createdOrder.setId("order-123");
        createdOrder.setTotal(100.0);
        createdOrder.setStatus("PENDING");
        // We can set dummy values for date etc if needed, mapper usually handles nulls
        // gracefully

        when(userOrderService.create(any(UserOrder.class))).thenReturn(createdOrder);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("order-123"))
                .andExpect(jsonPath("$.total").value(100.0))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}

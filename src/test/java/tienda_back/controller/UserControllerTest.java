package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.controller.webmodel.request.LoginRequest;
import tienda_back.controller.webmodel.request.RegisterRequest;
import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.Token;
import tienda_back.domain.model.User;
import tienda_back.domain.service.TokenService;
import tienda_back.domain.service.UserService;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class RegisterTests {
        @Test
        void register_ShouldReturnOk_WhenRegistrationIsSuccessful() throws Exception {
            RegisterRequest request = new RegisterRequest(
                    "Test User",
                    "test@example.com",
                    "password123",
                    "Test Address",
                    "555-0100");

            mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Usuario registrado correctamente"));
        }
    }

    @Nested
    class LoginTests {
        @Test
        void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() throws Exception {
            LoginRequest request = new LoginRequest("test@example.com", "password123");
            UUID userId = UUID.randomUUID();
            User user = new User(userId, "Test User", "test@example.com", "password123", "Address", "Phone",
                    RoleUser.CUSTOMER);

            String tokenValue = "generated-jwt-token";
            Token token = new Token(UUID.randomUUID(), tokenValue, userId, Instant.now());

            when(userService.login(any(UserLoginDto.class))).thenReturn(user);
            when(tokenService.generate(userId)).thenReturn(token);

            mockMvc.perform(post("/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value(tokenValue));
        }
    }
}

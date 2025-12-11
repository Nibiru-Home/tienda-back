package tienda_back.controller.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.controller.webmodel.request.LoginRequest;
import tienda_back.controller.webmodel.request.RegisterRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.AuthResponse;
import tienda_back.controller.webmodel.response.UserResponse;
import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        UserMapper instance1 = UserMapper.getInstance();
        UserMapper instance2 = UserMapper.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testToRegisterDto_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        RegisterRequest request = new RegisterRequest(
                "John Doe",
                "john@example.com",
                "password123",
                "123 Main St",
                "555-1234");

        UserRegisterDto result = mapper.toRegisterDto(request);

        assertNotNull(result);
        assertEquals(request.name(), result.name());
        assertEquals(request.email(), result.email());
        assertEquals(request.password(), result.password());
        assertEquals(request.address(), result.address());
        assertEquals(request.phone(), result.phone());
    }

    @Test
    void testUserRequestToUserDto_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        UserRequest request = new UserRequest(
                "Jane Doe",
                "jane@example.com",
                "pass456",
                "456 Oak Ave",
                "555-5678");

        UserRegisterDto result = mapper.userRequestToUserDto(request);

        assertNotNull(result);
        assertEquals(request.name(), result.name());
        assertEquals(request.email(), result.email());
        assertEquals(request.password(), result.password());
        assertEquals(request.address(), result.address());
        assertEquals(request.phone(), result.phone());
    }

    @Test
    void testUserDtoToUserResponse_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        UserRegisterDto dto = new UserRegisterDto(
                "Bob Smith",
                "bob@example.com",
                "securePass",
                "789 Pine Rd",
                "555-9012");

        UserResponse result = mapper.userDtoToUserResponse(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.name());
        assertEquals(dto.email(), result.email());
        assertEquals(dto.address(), result.address());
        assertEquals(dto.phone(), result.phone());
    }

    @Test
    void testToAuthResponse_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        User user = new User(
                UUID.randomUUID(),
                "Alice",
                "alice@example.com",
                "pass",
                "Addr",
                "Phone",
                RoleUser.CUSTOMER);
        String token = "jwt-token-string";

        AuthResponse result = mapper.toAuthResponse(user, token);

        assertNotNull(result);
        assertEquals(token, result.token());
    }

    @Test
    void testToLoginDto_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        LoginRequest request = new LoginRequest("test@test.com", "secret");

        UserLoginDto result = mapper.toLoginDto(request);

        assertNotNull(result);
        assertEquals(request.email(), result.email());
        assertEquals(request.password(), result.password());
    }
}

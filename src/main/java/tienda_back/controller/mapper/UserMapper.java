package tienda_back.controller.mapper;

import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.dto.UserDto;
import tienda_back.domain.model.User;
import tienda_back.controller.webmodel.request.LoginRequest;
import tienda_back.controller.webmodel.request.RegisterRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.AuthResponse;
import tienda_back.controller.webmodel.response.UserResponse;

public class UserMapper {

    private static UserMapper INSTANCE;

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }

    public UserRegisterDto toRegisterDto(RegisterRequest request) {
        return new UserRegisterDto(
                request.name(),
                request.email(),
                request.password(),
                request.address(),
                request.phone());
    }

    public UserDto userRequestToUserDto(UserRequest request) {
        // Warning: ID and Role are not in UserRequest, setting null/default
        // If UserRequest represents an existing user, we might need ID.
        // For Cart creation, maybe we don't need full user info if we have ID.
        // Assuming strict mapping of available fields.
        return new UserDto(
                null, // ID unknown from UserRequest usually or handled elsewhere
                request.name(),
                request.email(),
                request.address(),
                request.phone(),
                null // Role unknown
        );
    }

    public UserResponse userDtoToUserResponse(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserResponse(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.address(),
                dto.phone(),
                dto.role() != null ? dto.role().toString() : "CUSTOMER");
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().toString() : "CUSTOMER");
    }

    public AuthResponse toAuthResponse(User user, String token) {
        UserResponse userResponse = toUserResponse(user);
        return new AuthResponse(token, userResponse);
    }

    public UserLoginDto toLoginDto(LoginRequest request) {
        return new UserLoginDto(
                request.email(),
                request.password());
    }
}

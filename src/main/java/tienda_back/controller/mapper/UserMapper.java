package tienda_back.controller.mapper;

import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.User;
import tienda_back.controller.webmodel.request.LoginRequest;
import tienda_back.controller.webmodel.request.RegisterRequest;
import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.AuthResponse;
import tienda_back.controller.webmodel.response.UserResponse;

public class UserMapper {

    private static UserMapper INSTANCE;

    private UserMapper() {}

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
                request.phone()
        );
    }

    public UserRegisterDto userRequestToUserDto(UserRequest request) {
        return new UserRegisterDto(
                request.name(),
                request.email(),
                request.password(),
                request.address(),
                request.phone()
        );
    }

    public UserResponse userDtoToUserResponse(UserRegisterDto dto) {
        return new UserResponse(
                dto.name(),
                dto.email(),
                dto.address(),
                dto.phone()
        );
    }

    public AuthResponse toAuthResponse(User user, String token) {
        return new AuthResponse(token);
    }

    public tienda_back.domain.dto.UserLoginDto toLoginDto(LoginRequest request) {
        return new tienda_back.domain.dto.UserLoginDto(
                request.email(),
                request.password()
        );
    }
}

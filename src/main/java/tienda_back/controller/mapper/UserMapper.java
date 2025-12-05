package tienda_back.controller.mapper;

import tienda_back.controller.webmodel.request.UserRequest;
import tienda_back.controller.webmodel.response.UserResponse;
import tienda_back.domain.dto.UserDto;

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

    public UserDto userRequestToUserDto(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }

        return new UserDto(
                userRequest.id(),
                userRequest.name(),
                userRequest.email(),
                userRequest.password(),
                userRequest.address(),
                userRequest.phone(),
                userRequest.role()
        );
    }

    public UserRequest userDtoToUserRequest(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new UserRequest(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.password(),
                userDto.address(),
                userDto.phone(),
                userDto.role()
        );
    }

    public UserResponse userDtoToUserResponse(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new UserResponse(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.password(),
                userDto.address(),
                userDto.phone(),
                userDto.role()
        );
    }
}

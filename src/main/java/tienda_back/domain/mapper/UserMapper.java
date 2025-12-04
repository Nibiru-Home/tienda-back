package tienda_back.domain.mapper;

import tienda_back.domain.model.User;
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

    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getPhone(),
                user.getRole());
    }

    public User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.password(),
                userDto.address(),
                userDto.phone(),
                userDto.role());
    }
}

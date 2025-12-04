package tienda_back.domain.mapper;

import tienda_back.domain.model.user;
import tienda_back.domain.dto.userDto;

public class UserMapper {
    private static UserMapper INSTANCE;

    private UserMapper() {}

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }

    public userDto userToUserDto(user user) {
        if (user == null) {
            return null;
        }

        return new userDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getPhone(),
                user.getRole()
        );
    }

    public user userDtoToUser(userDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new user(
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

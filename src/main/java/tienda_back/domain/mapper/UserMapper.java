package tienda_back.domain.mapper;

import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;

import java.util.UUID;

import tienda_back.domain.dto.UserRegisterDto;

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

    public UserRegisterDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserRegisterDto(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getPhone());
    }

    public User userDtoToUser(UserRegisterDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                UUID.randomUUID(),
                userDto.name(),
                userDto.email(),
                userDto.password(),
                userDto.address(),
                userDto.phone(),
                RoleUser.CUSTOMER);
    }

    public User fromRegister(UserRegisterDto userRegisterDto, String passwordHash) {
        if (userRegisterDto == null) {
            return null;
        }
        return new User(
                UUID.randomUUID(),
                userRegisterDto.name(),
                userRegisterDto.email(),
                passwordHash,
                userRegisterDto.address(),
                userRegisterDto.phone(),
                RoleUser.CUSTOMER);
    }
}

package tienda_back.domain.mapper;

import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;

import java.util.UUID;

import tienda_back.domain.dto.UserRegisterDto;
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

    public UserRegisterDto userToUserRegisterDto(User user) {
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

    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getRole());
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

    public User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.id() != null ? userDto.id() : UUID.randomUUID(),
                userDto.name(),
                userDto.email(),
                "defaultPassword", 
                userDto.address(),
                userDto.phone(),
                userDto.role());
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

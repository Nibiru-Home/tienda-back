package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
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
        assertSame(instance1, instance2, "Should return same singleton instance");
    }

    @Test
    void testUserToUserDto_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        User user = new User(
                UUID.randomUUID(),
                "John",
                "john@example.com",
                "secret",
                "Address",
                "123456",
                RoleUser.CUSTOMER);

        UserRegisterDto result = mapper.userToUserDto(user);

        assertNotNull(result);
        assertEquals(user.getName(), result.name());
        assertEquals(user.getEmail(), result.email());
        assertEquals(user.getPassword(), result.password());
        assertEquals(user.getAddress(), result.address());
        assertEquals(user.getPhone(), result.phone());
    }

    @Test
    void testUserToUserDto_WithNull_ReturnsNull() {
        UserMapper mapper = UserMapper.getInstance();
        assertNull(mapper.userToUserDto(null));
    }

    @Test
    void testUserDtoToUser_MapsCorrectly() {
        UserMapper mapper = UserMapper.getInstance();
        UserRegisterDto dto = new UserRegisterDto(
                "Jane",
                "jane@example.com",
                "pass",
                "Addr 2",
                "987654");

        User result = mapper.userDtoToUser(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.email(), result.getEmail());
        assertEquals(dto.password(), result.getPassword());
        assertEquals(dto.address(), result.getAddress());
        assertEquals(dto.phone(), result.getPhone());
        assertEquals(RoleUser.CUSTOMER, result.getRole());
        assertNotNull(result.getId());
    }

    @Test
    void testUserDtoToUser_WithNull_ReturnsNull() {
        UserMapper mapper = UserMapper.getInstance();
        assertNull(mapper.userDtoToUser(null));
    }

    @Test
    void testFromRegister_MapsCorrectlyWithPasswordHash() {
        UserMapper mapper = UserMapper.getInstance();
        UserRegisterDto dto = new UserRegisterDto(
                "Bob",
                "bob@example.com",
                "plainPassword",
                "Addr 3",
                "111222");
        String passwordHash = "hashedPassword123";

        User result = mapper.fromRegister(dto, passwordHash);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.email(), result.getEmail());
        assertEquals(passwordHash, result.getPassword()); 
        assertEquals(dto.address(), result.getAddress());
        assertEquals(dto.phone(), result.getPhone());
        assertEquals(RoleUser.CUSTOMER, result.getRole());
        assertNotNull(result.getId());
    }

    @Test
    void testFromRegister_WithNullDto_ReturnsNull() {
        UserMapper mapper = UserMapper.getInstance();
        assertNull(mapper.fromRegister(null, "hash"));
    }
}

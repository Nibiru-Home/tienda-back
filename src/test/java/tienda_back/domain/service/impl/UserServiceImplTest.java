package tienda_back.domain.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.exception.LoginFailedException;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.exception.UserAlreadyExistsException;
import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;
import tienda_back.domain.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : encoded)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class GetAllTests {
        @Test
        void getAll_ShouldReturnListOfUsers() {
            User user1 = new User(UUID.randomUUID(), "User1", "u1@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);
            User user2 = new User(UUID.randomUUID(), "User2", "u2@test.com", "pass", "addr", "222", RoleUser.CUSTOMER);
            when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

            List<User> result = userService.getAll();

            assertEquals(2, result.size());
            verify(userRepository).findAll();
        }
    }

    @Nested
    class GetByIdTests {
        @Test
        void getById_WithExistingId_ShouldReturnUser() {
            UUID id = UUID.randomUUID();
            User user = new User(id, "User", "u@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);
            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            User result = userService.getById(id);

            assertNotNull(result);
            assertEquals(id, result.getId());
        }

        @Test
        void getById_WithNonExistingId_ShouldThrowException() {
            UUID id = UUID.randomUUID();
            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
        }
    }

    @Nested
    class CreateTests {
        @Test
        void create_ShouldSaveAndReturnUser() {
            User user = new User(UUID.randomUUID(), "User", "u@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);
            when(userRepository.save(user)).thenReturn(user);

            User result = userService.create(user);

            assertEquals(user, result);
            verify(userRepository).save(user);
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void update_WithExistingUser_ShouldUpdateAndReturnUser() {
            UUID id = UUID.randomUUID();
            User user = new User(id, "User Updated", "u@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);
            when(userRepository.existsById(id)).thenReturn(true);
            when(userRepository.update(user)).thenReturn(user);

            User result = userService.update(user);

            assertEquals(user, result);
            verify(userRepository).update(user);
        }

        @Test
        void update_WithNonExistingUser_ShouldThrowException() {
            UUID id = UUID.randomUUID();
            User user = new User(id, "User", "u@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);
            when(userRepository.existsById(id)).thenReturn(false);

            assertThrows(ResourceNotFoundException.class, () -> userService.update(user));
            verify(userRepository, never()).update(any());
        }

        @Test
        void update_WithNullId_ShouldThrowException() {
            User user = new User(null, "User", "u@test.com", "pass", "addr", "111", RoleUser.CUSTOMER);

            assertThrows(ResourceNotFoundException.class, () -> userService.update(user));
        }
    }

    @Nested
    class DeleteByIdTests {
        @Test
        void deleteById_WithExistingId_ShouldDelete() {
            UUID id = UUID.randomUUID();
            when(userRepository.existsById(id)).thenReturn(true);

            userService.deleteById(id);

            verify(userRepository).deleteById(id);
        }

        @Test
        void deleteById_WithNonExistingId_ShouldThrowException() {
            UUID id = UUID.randomUUID();
            when(userRepository.existsById(id)).thenReturn(false);

            assertThrows(ResourceNotFoundException.class, () -> userService.deleteById(id));
            verify(userRepository, never()).deleteById(any());
        }
    }

    @Nested
    class GetByEmailTests {
        @Test
        void getByEmail_WithExistingEmail_ShouldReturnUser() {
            String email = "test@example.com";
            User user = new User(UUID.randomUUID(), "User", email, "pass", "addr", "111", RoleUser.CUSTOMER);
            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

            User result = userService.getByEmail(email);

            assertNotNull(result);
            assertEquals(email, result.getEmail());
        }

        @Test
        void getByEmail_WithNonExistingEmail_ShouldThrowLoginFailedException() {
            String email = "missing@example.com";
            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            assertThrows(LoginFailedException.class, () -> userService.getByEmail(email));
        }
    }

    @Nested
    class LoginTests {
        @Test
        void login_WithValidCredentials_ShouldReturnUser() {
            String email = "valid@example.com";
            String password = "mypassword";
            String hashed = hashPassword(password);
            User user = new User(UUID.randomUUID(), "User", email, hashed, "addr", "111", RoleUser.CUSTOMER);
            UserLoginDto loginDto = new UserLoginDto(email, password);

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

            User result = userService.login(loginDto);

            assertEquals(user, result);
        }

        @Test
        void login_WithWrongPassword_ShouldThrowException() {
            String email = "valid@example.com";
            String password = "wrongpassword";
            String storedHash = hashPassword("correctpassword");
            User user = new User(UUID.randomUUID(), "User", email, storedHash, "addr", "111", RoleUser.CUSTOMER);
            UserLoginDto loginDto = new UserLoginDto(email, password);

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

            LoginFailedException ex = assertThrows(LoginFailedException.class, () -> userService.login(loginDto));
            assertEquals("La contraseña no es correcta", ex.getMessage());
        }

        @Test
        void login_WithNonExistingUser_ShouldThrowException() {
            String email = "missing@example.com";
            UserLoginDto loginDto = new UserLoginDto(email, "any");
            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            LoginFailedException ex = assertThrows(LoginFailedException.class, () -> userService.login(loginDto));
            assertEquals("El usuario no existe", ex.getMessage());
        }
    }

    @Nested
    class RegisterTests {
        @Test
        void register_WithNewUser_ShouldHashPasswordAndSave() {
            UserRegisterDto dto = new UserRegisterDto("New User", "new@example.com", "plainPass", "Addr", "123");
            when(userRepository.existsByEmail(dto.email())).thenReturn(false);

            userService.register(dto);

            verify(userRepository).save(argThat(user -> user.getEmail().equals(dto.email()) &&
                    !user.getPassword().equals(dto.password()) &&
                    user.getName().equals(dto.name()) &&
                    user.getId() != null 
            ));
        }

        @Test
        void register_WithExistingEmail_ShouldThrowException() {
            UserRegisterDto dto = new UserRegisterDto("Existing", "exists@example.com", "pass", "Addr", "123");
            when(userRepository.existsByEmail(dto.email())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> userService.register(dto));
            verify(userRepository, never()).save(any());
        }
    }
}

package tienda_back.persistence.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.domain.model.RoleUser;
import tienda_back.domain.model.User;
import tienda_back.persistence.dao.jpa.UserJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.repository.impl.UserRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaDao userJpaDao;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Nested
    class FindAllTests {
        @Test
        void findAll_ShouldReturnDomainUsers() {
            UserJpaEntity entity = new UserJpaEntity();
            entity.setId(UUID.randomUUID());
            entity.setName("Test User");
            entity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.findAll(0, 1000)).thenReturn(Collections.singletonList(entity));

            List<User> result = userRepository.findAll();

            assertEquals(1, result.size());
            assertEquals(entity.getId(), result.get(0).getId());
            assertEquals(entity.getName(), result.get(0).getName());
        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void findById_WithExistingUser_ShouldReturnOptionalUser() {
            UUID id = UUID.randomUUID();
            UserJpaEntity entity = new UserJpaEntity();
            entity.setId(id);
            entity.setName("Found");
            entity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.findById(id)).thenReturn(Optional.of(entity));

            Optional<User> result = userRepository.findById(id);

            assertTrue(result.isPresent());
            assertEquals(id, result.get().getId());
        }

        @Test
        void findById_WithNonExistingUser_ShouldReturnEmpty() {
            UUID id = UUID.randomUUID();
            when(userJpaDao.findById(id)).thenReturn(Optional.empty());

            Optional<User> result = userRepository.findById(id);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    class FindByEmailTests {
        @Test
        void findByEmail_WithExistingUser_ShouldReturnOptionalUser() {
            String email = "test@example.com";
            UserJpaEntity entity = new UserJpaEntity();
            entity.setId(UUID.randomUUID());
            entity.setEmail(email);
            entity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.findByEmail(email)).thenReturn(Optional.of(entity));

            Optional<User> result = userRepository.findByEmail(email);

            assertTrue(result.isPresent());
            assertEquals(email, result.get().getEmail());
        }
    }

    @Nested
    class SaveTests {
        @Test
        void save_WithNewUser_ShouldInsertAndReturnUser() {
            User user = new User(null, "New", "n@t.com", "pass", "addr", "123", RoleUser.CUSTOMER);
            UserJpaEntity savedEntity = new UserJpaEntity();
            savedEntity.setId(UUID.randomUUID());
            savedEntity.setName("New");
            savedEntity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.insert(any(UserJpaEntity.class))).thenReturn(savedEntity);

            User result = userRepository.save(user);

            assertNotNull(result.getId());
            verify(userJpaDao).insert(any(UserJpaEntity.class));
            verify(userJpaDao, never()).update(any());
        }

        @Test
        void save_WithExistingUser_ShouldUpdateAndReturnUser() {
            UUID id = UUID.randomUUID();
            User user = new User(id, "Update", "u@t.com", "pass", "addr", "123", RoleUser.CUSTOMER);
            UserJpaEntity updatedEntity = new UserJpaEntity();
            updatedEntity.setId(id);
            updatedEntity.setName("Update");
            updatedEntity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.update(any(UserJpaEntity.class))).thenReturn(updatedEntity);

            User result = userRepository.save(user);

            assertEquals(id, result.getId());
            verify(userJpaDao).update(any(UserJpaEntity.class));
            verify(userJpaDao, never()).insert(any());
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void update_ShouldDelegatToSave() {
            // Since update implementation just calls save, we verify the specific behavior
            // or just rely on save tests. However, let's test it specifically as per
            // interface.
            UUID id = UUID.randomUUID();
            User user = new User(id, "Update", "u@t.com", "pass", "addr", "123", RoleUser.CUSTOMER);
            UserJpaEntity updatedEntity = new UserJpaEntity();
            updatedEntity.setId(id);
            updatedEntity.setRole(RoleUser.CUSTOMER);

            when(userJpaDao.update(any(UserJpaEntity.class))).thenReturn(updatedEntity);

            User result = userRepository.update(user);

            assertNotNull(result);
            verify(userJpaDao).update(any(UserJpaEntity.class));
        }
    }

    @Nested
    class DeleteByIdTests {
        @Test
        void deleteById_ShouldDelegateToDao() {
            UUID id = UUID.randomUUID();
            userRepository.deleteById(id);
            verify(userJpaDao).deleteById(id);
        }
    }

    @Nested
    class ExistsTests {
        @Test
        void existsById_ShouldReturnTrueIfPresent() {
            UUID id = UUID.randomUUID();
            when(userJpaDao.findById(id)).thenReturn(Optional.of(new UserJpaEntity()));
            assertTrue(userRepository.existsById(id));
        }

        @Test
        void existsById_ShouldReturnFalseIfEmpty() {
            UUID id = UUID.randomUUID();
            when(userJpaDao.findById(id)).thenReturn(Optional.empty());
            assertFalse(userRepository.existsById(id));
        }

        @Test
        void existsByEmail_ShouldReturnTrueIfPresent() {
            String email = "e@mail.com";
            when(userJpaDao.findByEmail(email)).thenReturn(Optional.of(new UserJpaEntity()));
            assertTrue(userRepository.existsByEmail(email));
        }

        @Test
        void existsByEmail_ShouldReturnFalseIfEmpty() {
            String email = "e@mail.com";
            when(userJpaDao.findByEmail(email)).thenReturn(Optional.empty());
            assertFalse(userRepository.existsByEmail(email));
        }
    }
}

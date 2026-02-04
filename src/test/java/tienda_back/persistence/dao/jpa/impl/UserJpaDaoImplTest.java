package tienda_back.persistence.dao.jpa.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tienda_back.domain.model.RoleUser;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UserJpaDaoImpl.class)
class UserJpaDaoImplTest {

    @Autowired
    private UserJpaDaoImpl userJpaDao;

    @Test
    void saveAndFindById_ShouldPersistAndRetrieveUser() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(java.util.UUID.randomUUID());
        entity.setName("Test");
        entity.setEmail("test@email.com");
        entity.setPassword("pass");
        entity.setAddress("addr");
        entity.setPhone("123");
        entity.setRole(RoleUser.CUSTOMER);

        UserJpaEntity saved = userJpaDao.insert(entity);
        assertNotNull(saved.getId());

        Optional<UserJpaEntity> found = userJpaDao.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getEmail(), found.get().getEmail());
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenExists() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(java.util.UUID.randomUUID());
        entity.setName("EmailUser");
        entity.setEmail("unique@email.com");
        entity.setPassword("pass");
        entity.setPhone("123");
        entity.setAddress("addr");
        entity.setRole(RoleUser.CUSTOMER);
        userJpaDao.insert(entity);

        Optional<UserJpaEntity> result = userJpaDao.findByEmail("unique@email.com");
        assertTrue(result.isPresent());
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(java.util.UUID.randomUUID());
        entity.setName("ExistsUser");
        entity.setEmail("exist@email.com");
        entity.setPassword("pass");
        entity.setPhone("123");
        entity.setAddress("addr");
        entity.setRole(RoleUser.CUSTOMER);
        userJpaDao.insert(entity);

        assertTrue(userJpaDao.existsByEmail("exist@email.com"));
        assertFalse(userJpaDao.existsByEmail("nonexistent@email.com"));
    }

    @Test
    void findByName_ShouldReturnUser_WhenExists() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(java.util.UUID.randomUUID());
        entity.setName("NameUser");
        entity.setEmail("name@email.com");
        entity.setPassword("pass");
        entity.setPhone("123");
        entity.setAddress("addr");
        entity.setRole(RoleUser.CUSTOMER);
        userJpaDao.insert(entity);

        Optional<UserJpaEntity> result = userJpaDao.findByName("NameUser");
        assertTrue(result.isPresent());
        assertEquals("NameUser", result.get().getName());
    }

    @Test
    void existsByName_ShouldReturnTrue_WhenExists() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(java.util.UUID.randomUUID());
        entity.setName("ExistsNameUser");
        entity.setEmail("existsname@email.com");
        entity.setPassword("pass");
        entity.setPhone("123");
        entity.setAddress("addr");
        entity.setRole(RoleUser.CUSTOMER);
        userJpaDao.insert(entity);

        assertTrue(userJpaDao.existsByName("ExistsNameUser"));
        assertFalse(userJpaDao.existsByName("NonExistentUser"));
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        for (int i = 0; i < 15; i++) {
            UserJpaEntity u = new UserJpaEntity();
            u.setId(java.util.UUID.randomUUID());
            u.setName("User " + i);
            u.setEmail("user" + i + "@test.com");
            u.setPassword("pass");
            u.setPhone("123");
            u.setAddress("addr");
            u.setRole(RoleUser.CUSTOMER);
            userJpaDao.insert(u);
        }

        List<UserJpaEntity> page1 = userJpaDao.findAll(1, 10);
        assertEquals(10, page1.size());

        List<UserJpaEntity> page2 = userJpaDao.findAll(2, 10);
        assertEquals(10, page2.size());
    }
}

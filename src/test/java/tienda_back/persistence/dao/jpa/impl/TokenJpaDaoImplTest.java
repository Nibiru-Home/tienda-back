package tienda_back.persistence.dao.jpa.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TokenJpaDaoImpl.class)
class TokenJpaDaoImplTest {

    @Autowired
    private TokenJpaDaoImpl tokenJpaDao;

    @Test
    void insertAndFindById_ShouldPersistAndRetrieveToken() {
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setValue("token-val");
        entity.setUserId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());

        TokenJpaEntity saved = tokenJpaDao.insert(entity);

        Optional<TokenJpaEntity> found = tokenJpaDao.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("token-val", found.get().getValue());
    }

    @Test
    void findByValue_ShouldReturnToken_WhenExists() {
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setValue("find-me");
        entity.setUserId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        tokenJpaDao.insert(entity);

        Optional<TokenJpaEntity> result = tokenJpaDao.findByValue("find-me");
        assertTrue(result.isPresent());
        assertEquals("find-me", result.get().getValue());
    }

    @Test
    void update_ShouldUpdateToken() {
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setValue("original");
        entity.setUserId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        tokenJpaDao.insert(entity);

        entity.setValue("updated");
        tokenJpaDao.update(entity);

        Optional<TokenJpaEntity> result = tokenJpaDao.findById(entity.getId());
        assertTrue(result.isPresent());
        assertEquals("updated", result.get().getValue());
    }

    @Test
    void deleteById_ShouldRemoveToken() {
        UUID id = UUID.randomUUID();
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setId(id);
        entity.setValue("to-delete");
        entity.setUserId(UUID.randomUUID());
        entity.setCreatedAt(Instant.now());
        tokenJpaDao.insert(entity);

        assertTrue(tokenJpaDao.findById(id).isPresent());

        tokenJpaDao.deleteById(id);

        assertFalse(tokenJpaDao.findById(id).isPresent());
    }
}

package tienda_back.persistence.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.domain.model.Token;
import tienda_back.persistence.dao.jpa.TokenJpaDao;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;
import tienda_back.persistence.repository.impl.TokenRepositoryImpl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenRepositoryImplTest {

    @Mock
    private TokenJpaDao tokenJpaDao;

    @InjectMocks
    private TokenRepositoryImpl tokenRepository;

    @Nested
    class SaveTests {
        @Test
        void save_WithNewToken_ShouldInsert() {
            Token token = new Token(null, "token-val", UUID.randomUUID(), Instant.now());

            tokenRepository.save(token);

            verify(tokenJpaDao).insert(any(TokenJpaEntity.class));
            verify(tokenJpaDao, never()).update(any());
        }

        @Test
        void save_WithExistingToken_ShouldUpdate() {
            Token token = new Token(UUID.randomUUID(), "token-val", UUID.randomUUID(), Instant.now());

            tokenRepository.save(token);

            verify(tokenJpaDao).update(any(TokenJpaEntity.class));
            verify(tokenJpaDao, never()).insert(any());
        }
    }

    @Nested
    class FindByValueTests {
        @Test
        void findByValue_WithExistingToken_ShouldReturnDomainToken() {
            String val = "token-val";
            TokenJpaEntity entity = new TokenJpaEntity(UUID.randomUUID(), val, UUID.randomUUID(), Instant.now());

            when(tokenJpaDao.findByValue(val)).thenReturn(Optional.of(entity));

            Optional<Token> result = tokenRepository.findByValue(val);

            assertTrue(result.isPresent());
            assertEquals(val, result.get().getValue());
        }

        @Test
        void findByValue_WithNonExistingToken_ShouldReturnEmpty() {
            String val = "missing";
            when(tokenJpaDao.findByValue(val)).thenReturn(Optional.empty());

            Optional<Token> result = tokenRepository.findByValue(val);

            assertFalse(result.isPresent());
        }
    }
}

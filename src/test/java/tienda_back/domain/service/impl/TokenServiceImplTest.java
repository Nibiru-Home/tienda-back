package tienda_back.domain.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tienda_back.domain.model.Token;
import tienda_back.domain.repository.TokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Nested
    class GenerateTests {
        @Test
        void generate_ShouldCreateAndSaveToken() {
            UUID userId = UUID.randomUUID();

            Token result = tokenService.generate(userId);

            assertNotNull(result);
            assertEquals(userId, result.getUserId());
            assertNotNull(result.getValue());
            assertNotNull(result.getCreatedAt());

            Instant now = Instant.now();
            assertTrue(result.getCreatedAt().isAfter(now));

            verify(tokenRepository).save(result);
        }
    }

    @Nested
    class ValidateTests {
        @Test
        void validate_WithValidToken_ShouldReturnTrue() {
            String tokenValue = "valid-token";
            Token token = mock(Token.class);
            when(token.isExpired()).thenReturn(false);

            when(tokenRepository.findByValue(tokenValue)).thenReturn(Optional.of(token));

            assertTrue(tokenService.validate(tokenValue));
        }

        @Test
        void validate_WithExpiredToken_ShouldReturnFalse() {
            String tokenValue = "expired-token";
            Token token = mock(Token.class);
            when(token.isExpired()).thenReturn(true);

            when(tokenRepository.findByValue(tokenValue)).thenReturn(Optional.of(token));

            assertFalse(tokenService.validate(tokenValue));
        }

        @Test
        void validate_WithNonExistingToken_ShouldReturnFalse() {
            String tokenValue = "missing-token";
            when(tokenRepository.findByValue(tokenValue)).thenReturn(Optional.empty());

            assertFalse(tokenService.validate(tokenValue));
        }
    }

    @Nested
    class ExtractUserIdTests {
        @Test
        void extractUserId_WithExistingToken_ShouldReturnUserId() {
            String tokenValue = "valid-token";
            UUID userId = UUID.randomUUID();
            Token token = new Token(UUID.randomUUID(), tokenValue, userId, Instant.now());

            when(tokenRepository.findByValue(tokenValue)).thenReturn(Optional.of(token));

            UUID result = tokenService.extractUserId(tokenValue);

            assertEquals(userId, result);
        }

        @Test
        void extractUserId_WithNonExistingToken_ShouldThrowException() {
            String tokenValue = "missing-token";
            when(tokenRepository.findByValue(tokenValue)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class, () -> tokenService.extractUserId(tokenValue));
            assertEquals("Token inválido", ex.getMessage());
        }
    }
}

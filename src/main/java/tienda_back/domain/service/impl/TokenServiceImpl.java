package tienda_back.domain.service.impl;

import tienda_back.domain.service.TokenService;
import tienda_back.domain.model.Token;
import tienda_back.domain.repository.TokenRepository;
import java.time.Instant;
import java.util.UUID;

public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token generate(UUID userId) {

        String value = UUID.randomUUID().toString();
        Instant now = Instant.now();

        Token token = new Token(
                UUID.randomUUID(),
                value,
                userId,
                now.plusSeconds(3600));

        tokenRepository.save(token);

        return token;
    }

    @Override
    public boolean validate(String tokenValue) {
        return tokenRepository.findByValue(tokenValue)
                .filter(t -> !t.isExpired())
                .isPresent();
    }

    @Override
    public UUID extractUserId(String tokenValue) {
        return tokenRepository.findByValue(tokenValue)
                .map(Token::getUserId)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
    }
}

package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.Token;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;

public class TokenMapper {

    private static TokenMapper INSTANCE;

    private TokenMapper() {}

    public static TokenMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TokenMapper();
        }
        return INSTANCE;
    }

    public Token toToken(TokenJpaEntity tokenJpaEntity) {
        if (tokenJpaEntity == null) return null;

        return new Token(
                tokenJpaEntity.getId(),
                tokenJpaEntity.getValue(),
                tokenJpaEntity.getUserId(),
                tokenJpaEntity.getCreatedAt()
        );
    }

    public TokenJpaEntity toTokenJpaEntity(Token token) {
        if (token == null) return null;

        return new TokenJpaEntity(
                token.getId(),
                token.getValue(),
                token.getUserId(),
                token.getCreatedAt()
        );
    }
}

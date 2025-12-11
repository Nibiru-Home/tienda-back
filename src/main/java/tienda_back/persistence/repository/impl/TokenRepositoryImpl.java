package tienda_back.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Token;
import tienda_back.domain.repository.TokenRepository;
import tienda_back.persistence.dao.jpa.TokenJpaDao;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;
import tienda_back.persistence.repository.mapper.TokenMapper;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaDao tokenJpaDao;

    public TokenRepositoryImpl(TokenJpaDao tokenJpaDao) {
        this.tokenJpaDao = tokenJpaDao;
    }

    @Override
    public void save(Token token) {
        TokenJpaEntity entity = TokenMapper.getInstance().toTokenJpaEntity(token);

        if (token.getId() == null) {
            tokenJpaDao.insert(entity);
        } else {
            tokenJpaDao.update(entity);
        }
    }

    @Override
    public Optional<Token> findByValue(String value) {
        return tokenJpaDao.findByValue(value)
                .map(TokenMapper.getInstance()::toToken);
    }
}

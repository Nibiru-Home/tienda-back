package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import tienda_back.persistence.dao.jpa.TokenJpaDao;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;

@Repository
public class TokenJpaDaoImpl implements TokenJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TokenJpaEntity> findByValue(String value) {
        var query = entityManager.createQuery(
                "SELECT t FROM TokenJpaEntity t WHERE t.value = :value",
                TokenJpaEntity.class
        );
        query.setParameter("value", value);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<TokenJpaEntity> findAll(int page, int size) {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<TokenJpaEntity> findById(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public TokenJpaEntity insert(TokenJpaEntity jpaEntity) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public TokenJpaEntity update(TokenJpaEntity jpaEntity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}

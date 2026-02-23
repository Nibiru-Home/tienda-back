package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import tienda_back.persistence.dao.jpa.TokenJpaDao;
import tienda_back.persistence.dao.jpa.entity.TokenJpaEntity;

public class TokenJpaDaoImpl implements TokenJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TokenJpaEntity> findByValue(String value) {
        var query = entityManager.createQuery(
                "SELECT t FROM TokenJpaEntity t WHERE t.value = :value",
                TokenJpaEntity.class);
        query.setParameter("value", value);

        return query.getResultStream().findFirst();
    }

    @Override
    public List<TokenJpaEntity> findAll(int page, int size) {
        return entityManager.createQuery("SELECT t FROM TokenJpaEntity t", TokenJpaEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<TokenJpaEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(TokenJpaEntity.class, id));
    }

    @Override
    public TokenJpaEntity insert(TokenJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public TokenJpaEntity update(TokenJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(UUID id) {
        TokenJpaEntity entity = entityManager.find(TokenJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}

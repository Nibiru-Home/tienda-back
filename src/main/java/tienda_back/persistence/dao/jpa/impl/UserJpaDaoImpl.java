package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tienda_back.persistence.dao.jpa.UserJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;

public class UserJpaDaoImpl implements UserJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserJpaEntity> findAll(int page, int size) {
        return entityManager.createQuery("SELECT u FROM UserJpaEntity u ORDER BY u.id ASC", UserJpaEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<UserJpaEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(UserJpaEntity.class, id));
    }

    @Override
    public Optional<UserJpaEntity> findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM UserJpaEntity u WHERE u.email = :email", UserJpaEntity.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public UserJpaEntity insert(UserJpaEntity user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public UserJpaEntity update(UserJpaEntity user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public void deleteById(UUID id) {
        UserJpaEntity user = entityManager.find(UserJpaEntity.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public boolean existsByEmail(String email) {
        var query = entityManager.createQuery(
                "SELECT COUNT(u) FROM UserJpaEntity u WHERE u.email = :email",
                Long.class
        );
        query.setParameter("email", email);

        Long count = query.getSingleResult();
        return count > 0;
    }

}

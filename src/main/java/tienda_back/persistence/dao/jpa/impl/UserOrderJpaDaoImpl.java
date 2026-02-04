package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import tienda_back.persistence.dao.jpa.UserOrderJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;

@Repository
public class UserOrderJpaDaoImpl implements UserOrderJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserOrderJpaEntity> findAll(int page, int size) {
        return entityManager.createQuery("SELECT u FROM UserOrderJpaEntity u", UserOrderJpaEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<UserOrderJpaEntity> findById(String id) {
        return Optional.ofNullable(entityManager.find(UserOrderJpaEntity.class, id));
    }

    @Override
    public UserOrderJpaEntity insert(UserOrderJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public UserOrderJpaEntity update(UserOrderJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(String id) {
        UserOrderJpaEntity entity = entityManager.find(UserOrderJpaEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public List<UserOrderJpaEntity> findByUser(UserJpaEntity user) {
        return entityManager
                .createQuery("SELECT u FROM UserOrderJpaEntity u WHERE u.user = :user", UserOrderJpaEntity.class)
                .setParameter("user", user)
                .getResultList();
    }

}

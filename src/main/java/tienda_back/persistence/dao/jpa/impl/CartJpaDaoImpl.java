package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.persistence.dao.jpa.CartJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartJpaEntity;

@Repository
public class CartJpaDaoImpl implements CartJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT c FROM CartJpaEntity c ORDER BY c.id";
        TypedQuery<CartJpaEntity> query = entityManager
                .createQuery(sql, CartJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<CartJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CartJpaEntity.class, id.intValue()));
    }

    @Override
    public CartJpaEntity insert(CartJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CartJpaEntity update(CartJpaEntity jpaEntity) {
        CartJpaEntity managed = entityManager.find(CartJpaEntity.class, jpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Cart with id " + jpaEntity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        CartJpaEntity entity = entityManager.find(CartJpaEntity.class, id.intValue());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}

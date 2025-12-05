package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.persistence.dao.jpa.CartProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;

@Repository
public class CartProductJpaDaoImpl implements CartProductJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartProductJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT cp FROM CartProductJpaEntity cp ORDER BY cp.id";
        TypedQuery<CartProductJpaEntity> query = entityManager
                .createQuery(sql, CartProductJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<CartProductJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CartProductJpaEntity.class, id.intValue()));
    }

    @Override
    public CartProductJpaEntity insert(CartProductJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CartProductJpaEntity update(CartProductJpaEntity jpaEntity) {
        CartProductJpaEntity managed = entityManager.find(CartProductJpaEntity.class, jpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("CartProduct with id " + jpaEntity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        CartProductJpaEntity entity = entityManager.find(CartProductJpaEntity.class, id.intValue());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}

package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.persistence.dao.jpa.ProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

@Repository
public class ProductJpaDaoImpl implements ProductJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT p FROM ProductJpaEntity p ORDER BY p.id";
        TypedQuery<ProductJpaEntity> query = entityManager
                .createQuery(sql, ProductJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<ProductJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(ProductJpaEntity.class, id.intValue()));
    }

    @Override
    public Optional<ProductJpaEntity> findBySlug(String slug) {
        String sql = "SELECT p FROM ProductJpaEntity p WHERE p.slug = :slug";
        TypedQuery<ProductJpaEntity> query = entityManager.createQuery(sql, ProductJpaEntity.class);
        query.setParameter("slug", slug);
        return query.getResultStream().findFirst();
    }

    @Override
    public ProductJpaEntity insert(ProductJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public ProductJpaEntity update(ProductJpaEntity jpaEntity) {
        ProductJpaEntity managed = entityManager.find(ProductJpaEntity.class, jpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Product with id " + jpaEntity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        ProductJpaEntity entity = entityManager.find(ProductJpaEntity.class, id.intValue());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public void deleteBySlug(String slug) {
        Optional<ProductJpaEntity> entity = findBySlug(slug);
        entity.ifPresent(entityManager::remove);
    }
}

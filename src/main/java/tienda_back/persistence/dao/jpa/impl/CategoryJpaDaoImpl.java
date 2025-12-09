package tienda_back.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.persistence.dao.jpa.CategoryJpaDao;
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;

public class CategoryJpaDaoImpl implements CategoryJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT c FROM CategoryJpaEntity c ORDER BY c.id";
        TypedQuery<CategoryJpaEntity> query = entityManager
                .createQuery(sql, CategoryJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<CategoryJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CategoryJpaEntity.class, id.intValue()));
    }

    @Override
    public Optional<CategoryJpaEntity> findByName(String name) {
        String sql = "SELECT c FROM CategoryJpaEntity c WHERE c.name = :name";
        TypedQuery<CategoryJpaEntity> query = entityManager.createQuery(sql, CategoryJpaEntity.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    @Override
    public CategoryJpaEntity insert(CategoryJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CategoryJpaEntity update(CategoryJpaEntity jpaEntity) {
        CategoryJpaEntity managed = entityManager.find(CategoryJpaEntity.class, jpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Category with id " + jpaEntity.getId() + " not found");
        }
        entityManager.flush();
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        CategoryJpaEntity entity = entityManager.find(CategoryJpaEntity.class, id.intValue());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

}

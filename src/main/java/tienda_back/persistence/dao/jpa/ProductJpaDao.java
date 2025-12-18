package tienda_back.persistence.dao.jpa;

import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

public interface ProductJpaDao extends GenericJpaDao<ProductJpaEntity, Long> {
    long count();

    java.util.List<ProductJpaEntity> findByCategoryId(Long categoryId);
}

package tienda_back.persistence.dao.jpa;

import java.util.Optional;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

public interface ProductJpaDao extends GenericJpaDao<ProductJpaEntity> {

    Optional<ProductJpaEntity> findBySlug(String slug);

    void deleteBySlug(String slug);
}

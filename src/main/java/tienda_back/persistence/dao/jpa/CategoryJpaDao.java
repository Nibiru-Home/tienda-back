package tienda_back.persistence.dao.jpa;

import java.util.Optional;
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;

public interface CategoryJpaDao extends GenericJpaDao<CategoryJpaEntity> {

    Optional<CategoryJpaEntity> findByName(String name);
}

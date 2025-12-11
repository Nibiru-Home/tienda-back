package tienda_back.persistence.dao.jpa;

import java.util.Optional;

import java.util.UUID;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;

public interface UserJpaDao extends GenericJpaDao<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);

}

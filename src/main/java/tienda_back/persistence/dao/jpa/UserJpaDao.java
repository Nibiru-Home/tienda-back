package tienda_back.persistence.dao.jpa;

import java.util.Optional;

import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;

public interface UserJpaDao extends GenericJpaDao<UserJpaEntity> {

    Optional<UserJpaEntity> findByEmail(String email);

}

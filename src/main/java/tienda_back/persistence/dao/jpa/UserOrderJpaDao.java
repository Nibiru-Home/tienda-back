package tienda_back.persistence.dao.jpa;

import java.util.List;

import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;

public interface UserOrderJpaDao extends GenericJpaDao<UserOrderJpaEntity, String> {
    List<UserOrderJpaEntity> findByUser(UserJpaEntity user);
}

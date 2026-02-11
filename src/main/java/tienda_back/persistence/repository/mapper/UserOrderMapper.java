package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.UserOrder;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;

public class UserOrderMapper {

    private static UserOrderMapper INSTANCE;

    private UserOrderMapper() {
    }

    public static UserOrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserOrderMapper();
        }
        return INSTANCE;
    }

    public UserOrder toDomain(UserOrderJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        UserOrder domain = new UserOrder();
        domain.setId(entity.getId());
        domain.setTotal(entity.getTotal());
        domain.setDate(entity.getDate());
        domain.setStatus(entity.getStatus());
        domain.setUser(UserMapper.getInstance().toUser(entity.getUser()));

        if (entity.getCart() != null) {
            domain.setCart(CartMapper.getInstance().cartJpaEntityToCart(entity.getCart()));
        }
        return domain;
    }

    public UserOrderJpaEntity toJpaEntity(UserOrder domain) {
        if (domain == null) {
            return null;
        }
        UserOrderJpaEntity entity = new UserOrderJpaEntity();
        entity.setId(domain.getId());
        entity.setTotal(domain.getTotal());
        entity.setDate(domain.getDate());
        entity.setStatus(domain.getStatus());
        entity.setUser(UserMapper.getInstance().toUserJpaEntity(domain.getUser()));
        if (domain.getCart() != null) {
            tienda_back.persistence.dao.jpa.entity.CartJpaEntity cartJpa = new tienda_back.persistence.dao.jpa.entity.CartJpaEntity();
            if (domain.getCart().getId() != null) {
                cartJpa.setId(domain.getCart().getId().intValue());
            }
            entity.setCart(cartJpa);
        }
        return entity;
    }
}

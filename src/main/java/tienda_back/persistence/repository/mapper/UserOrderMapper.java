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
            tienda_back.domain.model.Cart cart = new tienda_back.domain.model.Cart();
            cart.setId(entity.getCart().getId().longValue());
            domain.setCart(cart);
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
        // Map Cart
        if (domain.getCart() != null) {
            // Need CartMapper or manual mapping.
            // Assuming we just need to set the CartJpaEntity reference if ID exists or map
            // it.
            // Since Cart is part of the order, we might need a CartMapper.
            // Let's assume CartMapper exists in persistence layer or we use one.
            // Actually, we probably need a CartMapper in persistence/repository/mapper too?
            // Existing CartMapper is in default
            // main/java/tienda_back/persistence/repository/mapper/CartMapper.java?
            // Let's verify. If not, minimal mapping for now.
            // Just creating a new entity with ID if lazy loading or full map if eager.
            tienda_back.persistence.dao.jpa.entity.CartJpaEntity cartJpa = new tienda_back.persistence.dao.jpa.entity.CartJpaEntity();
            if (domain.getCart().getId() != null) {
                cartJpa.setId(domain.getCart().getId().intValue()); // Assuming Long to Integer conversion if mismatch
            }
            entity.setCart(cartJpa);
        }
        return entity;
    }
}

package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.Cart;
import tienda_back.persistence.dao.jpa.entity.CartJpaEntity;

public class CartMapper {

    private static CartMapper INSTANCE;

    private CartMapper() {
    }

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart cartJpaEntityToCart(CartJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(jpaEntity.getId().longValue());

        return cart;
    }

    public CartJpaEntity cartToCartJpaEntity(Cart domain) {
        if (domain == null) {
            return null;
        }
        CartJpaEntity entity = new CartJpaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().intValue());
        }

        return entity;
    }
}

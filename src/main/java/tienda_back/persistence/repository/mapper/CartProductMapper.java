package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.CartProduct;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;

public class CartProductMapper {

    private static CartProductMapper INSTANCE;

    private CartProductMapper() {
    }

    public static CartProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartProductMapper();
        }
        return INSTANCE;
    }

    public CartProduct cartProductJpaEntityToCartProduct(CartProductJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(jpaEntity.getId().longValue());
        return cartProduct;
    }

    public CartProductJpaEntity cartProductToCartProductJpaEntity(CartProduct domain) {
        if (domain == null) {
            return null;
        }
        CartProductJpaEntity entity = new CartProductJpaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().intValue());
        }
        return entity;
    }
}

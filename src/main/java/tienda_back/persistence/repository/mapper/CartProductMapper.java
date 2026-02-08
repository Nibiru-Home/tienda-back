package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.CartProduct;
import tienda_back.persistence.dao.jpa.entity.CartProductJpaEntity;
import tienda_back.persistence.dao.jpa.entity.CartJpaEntity;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

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
        cartProduct.setQuantity(jpaEntity.getQuantity() != null ? jpaEntity.getQuantity() : 0);
        cartProduct.setCart(CartMapper.getInstance().cartJpaEntityToCart(jpaEntity.getCart()));
        cartProduct.setProduct(ProductMapper.getInstance().productJpaEntityToProduct(jpaEntity.getProduct()));
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
        entity.setQuantity(domain.getQuantity());

        if (domain.getCart() != null && domain.getCart().getId() != null) {
            CartJpaEntity cartEntity = new CartJpaEntity();
            cartEntity.setId(domain.getCart().getId().intValue());
            entity.setCart(cartEntity);
        }

        if (domain.getProduct() != null && domain.getProduct().getId() != null) {
            ProductJpaEntity productEntity = new ProductJpaEntity();
            productEntity.setId(domain.getProduct().getId().intValue());
            entity.setProduct(productEntity);
        }

        return entity;
    }
}

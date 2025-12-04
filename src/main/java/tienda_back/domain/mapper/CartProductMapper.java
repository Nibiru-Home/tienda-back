package tienda_back.domain.mapper;

import tienda_back.domain.model.cartProduct;
import tienda_back.domain.dto.cartProductDto;

public class CartProductMapper {
    private static CartProductMapper INSTANCE;

    private CartProductMapper() {}

    public static CartProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartProductMapper();
        }
        return INSTANCE;
    }

    public cartProductDto cartProductToCartProductDto(cartProduct cartProduct) {
        if (cartProduct == null) {
            return null;
        }

        return new cartProductDto(
                cartProduct.getId(),
                cartProduct.getQuantity(),
                cartProduct.getCart(),
                cartProduct.getProduct()
        );
    }

    public cartProduct cartProductDtoToCartProduct(cartProductDto cartProductDto) {
        if (cartProductDto == null) {
            return null;
        }

        cartProduct cartProduct = new cartProduct();
        cartProduct.setId(cartProductDto.id());
        cartProduct.setQuantity(cartProductDto.quantity());
        cartProduct.setCart(cartProductDto.cart());
        cartProduct.setProduct(cartProductDto.product());

        return cartProduct;
    }
}

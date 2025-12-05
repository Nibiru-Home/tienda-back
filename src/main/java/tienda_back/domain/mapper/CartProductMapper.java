package tienda_back.domain.mapper;

import tienda_back.domain.model.CartProduct;
import tienda_back.domain.dto.CartProductDto;

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

    public CartProductDto cartProductToCartProductDto(CartProduct cartProduct) {
        if (cartProduct == null) {
            return null;
        }

        return new CartProductDto(
                cartProduct.getId(),
                cartProduct.getQuantity(),
                CartMapper.getInstance().cartToCartDto(cartProduct.getCart()),
                ProductMapper.getInstance().productToProductDto(cartProduct.getProduct()));
    }

    public CartProduct cartProductDtoToCartProduct(CartProductDto cartProductDto) {
        if (cartProductDto == null) {
            return null;
        }

        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(cartProductDto.id());
        cartProduct.setQuantity(cartProductDto.quantity());
        cartProduct.setCart(CartMapper.getInstance().cartDtoToCart(cartProductDto.cart()));
        cartProduct.setProduct(ProductMapper.getInstance().productDtoToProduct(cartProductDto.product()));

        return cartProduct;
    }
}

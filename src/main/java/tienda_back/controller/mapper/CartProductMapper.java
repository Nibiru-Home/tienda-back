package tienda_back.controller.mapper;

import tienda_back.domain.dto.CartProductDto;
import tienda_back.controller.webmodel.request.CartProductRequest;
import tienda_back.controller.webmodel.response.CartProductResponse;

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

    public CartProductDto cartProductRequestToCartProductDto(CartProductRequest cartProductRequest) {
        if (cartProductRequest == null) {
            return null;
        }

        return new CartProductDto(
                cartProductRequest.id(),
                cartProductRequest.quantity(),
                CartMapper.getInstance().cartRequestToCartDto(cartProductRequest.cart()),
                ProductMapper.getInstance().productRequestToProductDto(cartProductRequest.product()));
    }

    public CartProductResponse cartProductDtoToCartProductResponse(CartProductDto cartProductDto) {
        if (cartProductDto == null) {
            return null;
        }

        return new CartProductResponse(
                cartProductDto.id(),
                cartProductDto.quantity(),
                CartMapper.getInstance().cartDtoToCartResponse(cartProductDto.cart()),
                ProductMapper.getInstance().productDtoToProductResponse(cartProductDto.product()));
    }
}

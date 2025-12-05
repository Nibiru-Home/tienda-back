package tienda_back.controller.mapper;

import tienda_back.domain.dto.CartDto;
import tienda_back.controller.webmodel.request.CartRequest;
import tienda_back.controller.webmodel.response.CartResponse;

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

    public CartDto cartRequestToCartDto(CartRequest cartRequest) {
        if (cartRequest == null) {
            return null;
        }

        return new CartDto(
                cartRequest.id(),
                cartRequest.total(),
                cartRequest.price(),
                cartRequest.date(),
                cartRequest.status(),
                UserMapper.getInstance().userRequestToUserDto(cartRequest.user()));
    }

    public CartResponse cartDtoToCartResponse(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }

        return new CartResponse(
                cartDto.id(),
                cartDto.total(),
                cartDto.price(),
                cartDto.date(),
                cartDto.status(),
                UserMapper.getInstance().userDtoToUserResponse(cartDto.user()));
    }
}

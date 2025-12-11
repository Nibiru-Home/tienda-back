package tienda_back.controller.mapper;

import tienda_back.domain.dto.CartDto;
import tienda_back.controller.webmodel.request.CartRequest;
import tienda_back.controller.webmodel.response.CartResponse;

public class CartMapper {

    private static CartMapper INSTANCE;

    private CartMapper() {}

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public CartDto cartRequestToCartDto(CartRequest request) {
        if (request == null) {
            return null;
        }

        return new CartDto(
                request.id(),
                request.total(),
                request.price(),
                request.date(),
                request.status(),
                UserMapper.getInstance().userRequestToUserDto(request.user())
        );
    }

    public CartResponse cartDtoToCartResponse(CartDto dto) {
        if (dto == null) {
            return null;
        }

        return new CartResponse(
                dto.id(),
                dto.total(),
                dto.price(),
                dto.date(),
                dto.status(),
                UserMapper.getInstance().userDtoToUserResponse(dto.user())
        );
    }
}

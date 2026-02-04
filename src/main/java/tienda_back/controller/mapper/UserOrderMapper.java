package tienda_back.controller.mapper;

import tienda_back.controller.webmodel.request.UserOrderRequest;
import tienda_back.controller.webmodel.response.UserOrderResponse;
import tienda_back.domain.dto.UserOrderDto;
import tienda_back.domain.dto.UserDto;
import tienda_back.domain.dto.CartDto;

import java.util.UUID;

public class UserOrderMapper {

    private static UserOrderMapper INSTANCE;
    private final UserMapper userMapper = UserMapper.getInstance();

    private UserOrderMapper() {
    }

    public static UserOrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserOrderMapper();
        }
        return INSTANCE;
    }

    public UserOrderDto toDto(UserOrderRequest request) {
        if (request == null) {
            return null;
        }

        UserDto userDto = null;
        if (request.userId() != null) {
            try {
                // Temporary UserDto with ID only
                userDto = new UserDto(UUID.fromString(request.userId()), null, null, request.address(), null, null);
            } catch (IllegalArgumentException e) {
                // Ignore
            }
        }

        CartDto cartDto = null;
        if (request.cartId() != null) {
            // Create a dummy CartDto with just ID to be resolved by Service
            cartDto = new CartDto(request.cartId(), null, null, null, null, null);
        }

        return new UserOrderDto(
                null,
                userDto,
                null,
                null,
                null,
                cartDto);
    }

    public UserOrderResponse toResponse(UserOrderDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserOrderResponse(
                dto.id(),
                userMapper.userDtoToUserResponse(dto.user()),
                dto.total(),
                dto.date(),
                dto.status(),
                dto.cart());
    }
}

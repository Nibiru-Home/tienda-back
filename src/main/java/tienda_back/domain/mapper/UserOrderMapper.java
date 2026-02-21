package tienda_back.domain.mapper;

import tienda_back.domain.dto.UserOrderDto;
import tienda_back.domain.model.UserOrder;

public class UserOrderMapper {

    private static UserOrderMapper INSTANCE;
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CartMapper cartMapper = CartMapper.getInstance();

    private UserOrderMapper() {
    }

    public static UserOrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserOrderMapper();
        }
        return INSTANCE;
    }

    public UserOrderDto toDto(UserOrder domain) {
        if (domain == null) {
            return null;
        }

        return new UserOrderDto(
                domain.getId(),
                userMapper.userToUserDto(domain.getUser()),
                domain.getTotal(),
                domain.getDate(),
                domain.getStatus(),
                cartMapper.cartToCartDto(domain.getCart()));
    }

    public UserOrder toDomain(UserOrderDto dto) {
        if (dto == null) {
            return null;
        }
        UserOrder domain = new UserOrder();
        domain.setId(dto.id());
        domain.setTotal(dto.total());
        domain.setDate(dto.date());
        domain.setStatus(dto.status());
        domain.setUser(userMapper.userDtoToUser(dto.user()));
        domain.setCart(cartMapper.cartDtoToCart(dto.cart()));

        return domain;
    }

    // Reverse mapping if needed, though mostly we might create FROM request.
}

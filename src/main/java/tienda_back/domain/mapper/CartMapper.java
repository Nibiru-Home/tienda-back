package tienda_back.domain.mapper;

import tienda_back.domain.model.Cart;
import tienda_back.domain.dto.CartDto;

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

    public CartDto cartToCartDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        return new CartDto(
                cart.getId(),
                cart.getTotal(),
                cart.getPrice(),
                cart.getDate(),
                cart.getStatus(),
                UserMapper.getInstance().userToUserDto(cart.getUser()));
    }

    public Cart cartDtoToCart(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }

        Cart cart = new Cart();
        cart.setId(cartDto.id());
        cart.setTotal(cartDto.total());
        cart.setPrice(cartDto.price());
        cart.setDate(cartDto.date());
        cart.setStatus(cartDto.status());
        cart.setUser(UserMapper.getInstance().userDtoToUser(cartDto.user()));

        return cart;
    }
}

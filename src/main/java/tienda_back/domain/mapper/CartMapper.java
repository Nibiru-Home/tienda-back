package tienda_back.domain.mapper;

import tienda_back.domain.model.cart;
import tienda_back.domain.dto.cartDto;


public class CartMapper {
    private static CartMapper INSTANCE;

    private CartMapper() {}

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public cartDto cartToCartDto(cart cart) {
        if (cart == null) {
            return null;
        }

        return new cartDto(
                cart.getId(),
                cart.getTotal(),
                cart.getPrice(),
                cart.getDate(),
                cart.getStatus(),
                cart.getUser()
        );
    }

    public cart cartDtoToCart(cartDto cartDto) {
        if (cartDto == null) {
            return null;
        }

        cart cart = new cart();
        cart.setId(cartDto.id());
        cart.setTotal(cartDto.total());
        cart.setPrice(cartDto.price());
        cart.setDate(cartDto.date());
        cart.setStatus(cartDto.status());
        cart.setUser(cartDto.user());

        return cart;
    }
}

package tienda_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.mapper.CartMapper;
import tienda_back.domain.model.Cart;
import tienda_back.domain.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        List<Cart> carts = cartService.getAll();
        List<CartDto> cartDtos = carts.stream()
                .map(cart -> CartMapper.getInstance().cartToCartDto(cart))
                .toList();
        return new ResponseEntity<>(cartDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getById(id);
        CartDto cartDto = CartMapper.getInstance().cartToCartDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart cart = CartMapper.getInstance().cartDtoToCart(cartDto);
        Cart createdCart = cartService.create(cart);
        CartDto createdCartDto = CartMapper.getInstance().cartToCartDto(createdCart);
        return new ResponseEntity<>(createdCartDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto cartDto) {
        if (!id.equals(cartDto.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        Cart cart = CartMapper.getInstance().cartDtoToCart(cartDto);
        Cart updatedCart = cartService.update(cart);
        CartDto updatedCartDto = CartMapper.getInstance().cartToCartDto(updatedCart);
        return new ResponseEntity<>(updatedCartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

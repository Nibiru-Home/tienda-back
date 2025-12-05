package tienda_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda_back.domain.dto.CartProductDto;
import tienda_back.domain.mapper.CartProductMapper;
import tienda_back.domain.model.CartProduct;
import tienda_back.domain.service.CartProductService;

import java.util.List;

@RestController
@RequestMapping("/api/cart-products")
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @GetMapping
    public ResponseEntity<List<CartProductDto>> getAllCartProducts() {
        List<CartProduct> cartProducts = cartProductService.getAll();
        List<CartProductDto> cartProductDtos = cartProducts.stream()
                .map(cartProduct -> CartProductMapper.getInstance().cartProductToCartProductDto(cartProduct))
                .toList();
        return new ResponseEntity<>(cartProductDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartProductDto> getCartProductById(@PathVariable Long id) {
        CartProduct cartProduct = cartProductService.getById(id);
        CartProductDto cartProductDto = CartProductMapper.getInstance().cartProductToCartProductDto(cartProduct);
        return new ResponseEntity<>(cartProductDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartProductDto> createCartProduct(@RequestBody CartProductDto cartProductDto) {
        CartProduct cartProduct = CartProductMapper.getInstance().cartProductDtoToCartProduct(cartProductDto);
        CartProduct createdCartProduct = cartProductService.create(cartProduct);
        CartProductDto createdCartProductDto = CartProductMapper.getInstance()
                .cartProductToCartProductDto(createdCartProduct);
        return new ResponseEntity<>(createdCartProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartProductDto> updateCartProduct(@PathVariable Long id,
            @RequestBody CartProductDto cartProductDto) {
        if (!id.equals(cartProductDto.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        CartProduct cartProduct = CartProductMapper.getInstance().cartProductDtoToCartProduct(cartProductDto);
        CartProduct updatedCartProduct = cartProductService.update(cartProduct);
        CartProductDto updatedCartProductDto = CartProductMapper.getInstance()
                .cartProductToCartProductDto(updatedCartProduct);
        return new ResponseEntity<>(updatedCartProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

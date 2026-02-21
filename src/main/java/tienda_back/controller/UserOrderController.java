package tienda_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda_back.controller.mapper.UserOrderMapper;
import tienda_back.controller.webmodel.request.UserOrderRequest;
import tienda_back.controller.webmodel.response.UserOrderResponse;
import tienda_back.domain.dto.CartDto;
import tienda_back.domain.dto.CartProductDto;
import tienda_back.domain.dto.UserOrderDto;
import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.service.CartProductService;
import tienda_back.domain.service.UserOrderService;
import tienda_back.domain.service.UserService;
import tienda_back.domain.mapper.CartProductMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;
    private final UserService userService;
    private final CartProductService cartProductService;
    private final UserOrderMapper controllerMapper = UserOrderMapper.getInstance();
    private final tienda_back.domain.mapper.UserOrderMapper domainMapper = tienda_back.domain.mapper.UserOrderMapper
            .getInstance();

    public UserOrderController(UserOrderService userOrderService, UserService userService,
            CartProductService cartProductService) {
        this.userOrderService = userOrderService;
        this.userService = userService;
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<UserOrderResponse> createOrder(@RequestBody UserOrderRequest request) {
        UserOrderDto dto = controllerMapper.toDto(request);
        UserOrder domain = domainMapper.toDomain(dto);

        // Note: For product details (price, etc.), Service should handle enrichment or
        // validation
        // based on product IDs if validation logic is there.
        // Currently we trust request or partial mapping.
        // Usually creation involves logic processing inside Service.

        UserOrder created = userOrderService.create(domain);
        return new ResponseEntity<>(toResponseWithItems(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserOrderResponse>> getAllOrders() {
        List<UserOrderResponse> responseList = userOrderService.getAll().stream()
                .map(this::toResponseWithItems)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserOrderResponse>> getOrdersByUser(@PathVariable String userId) {
        // Assuming userId is UUID string
        User user = userService.getById(UUID.fromString(userId));
        List<UserOrder> orders = userOrderService.getByUser(user);

        List<UserOrderResponse> responseList = orders.stream()
                .map(this::toResponseWithItems)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOrderResponse> getOrderById(@PathVariable String id) {
        UserOrder order = userOrderService.getById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toResponseWithItems(order), HttpStatus.OK);
    }

    private UserOrderResponse toResponseWithItems(UserOrder order) {
        UserOrderDto dto = domainMapper.toDto(order);
        if (dto == null) {
            return null;
        }
        return controllerMapper.toResponse(enrichOrderDtoWithItems(order, dto));
    }

    private UserOrderDto enrichOrderDtoWithItems(UserOrder order, UserOrderDto dto) {
        if (order == null || order.getCart() == null || order.getCart().getId() == null || dto.cart() == null) {
            return dto;
        }

        List<CartProductDto> items = cartProductService.getByCart(order.getCart()).stream()
                .map(cp -> CartProductMapper.getInstance().cartProductToCartProductDto(cp))
                .collect(Collectors.toList());

        CartDto cart = dto.cart();
        CartDto enrichedCart = new CartDto(
                cart.id(),
                cart.total(),
                cart.price(),
                cart.date(),
                cart.status(),
                cart.user(),
                items);

        return new UserOrderDto(
                dto.id(),
                dto.user(),
                dto.total(),
                dto.date(),
                dto.status(),
                enrichedCart);
    }
}

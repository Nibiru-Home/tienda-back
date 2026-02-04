package tienda_back.controller.webmodel.request;

public record UserOrderRequest(
                String userId,
                Long cartId,
                String address // Optional shipping address different from user default
) {
}

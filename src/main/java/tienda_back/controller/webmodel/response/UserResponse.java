package tienda_back.controller.webmodel.response;

public record UserResponse(
        String name,
        String email,
        String address,
        String phone
) {}

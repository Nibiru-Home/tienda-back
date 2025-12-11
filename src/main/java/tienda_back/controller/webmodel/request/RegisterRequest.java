package tienda_back.controller.webmodel.request;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String address,
        String phone
) {}

package tienda_back.controller.webmodel.request;

public record LoginRequest(
        String email,
        String password
) {}

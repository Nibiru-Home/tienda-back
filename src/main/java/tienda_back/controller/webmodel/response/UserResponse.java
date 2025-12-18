package tienda_back.controller.webmodel.response;

public record UserResponse(
                java.util.UUID id,
                String name,
                String email,
                String address,
                String phone,
                String role) {
}

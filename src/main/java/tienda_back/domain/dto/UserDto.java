package tienda_back.domain.dto;

import java.util.UUID;
import tienda_back.domain.model.RoleUser;

public record UserDto(
        UUID id,
        String name,
        String email,
        String address,
        String phone,
        RoleUser role) {
}

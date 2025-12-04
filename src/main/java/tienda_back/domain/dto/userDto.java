package tienda_back.domain.dto;

import tienda_back.domain.model.RoleUser;

public record UserDto(
        Long id,
        String name,
        String email,
        String password,
        String address,
        String phone,
        RoleUser role) {

}

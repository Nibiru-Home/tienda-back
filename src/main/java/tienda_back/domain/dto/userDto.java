package tienda_back.domain.dto;

import tienda_back.domain.model.roleUser;

public record userDto (
    Long id,
    String name,
    String email,
    String password,
    String address,
    String phone,
    roleUser role
) {

}


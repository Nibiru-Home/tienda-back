package tienda_back.controller.webmodel.request;

import tienda_back.domain.model.RoleUser;

public record UserRequest (
    Long id,
    String name,
    String email,
    String password,
    String address,
    String phone,
    RoleUser role)
{
}

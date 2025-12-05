package tienda_back.controller.webmodel.response;

import tienda_back.domain.model.RoleUser;
public record UserResponse (
    Long id,
    String name,
    String email,
    String password,
    String address,
    String phone,
    RoleUser role
){

}

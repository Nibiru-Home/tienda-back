package tienda_back.domain.dto;

public record UserRegisterDto(
        String name,
        String email,
        String password,
        String address,
        String phone) {

}

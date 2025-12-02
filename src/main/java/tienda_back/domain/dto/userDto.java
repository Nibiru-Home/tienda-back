package tienda_back.domain.dto;

public record userDto (
    Long id,
    String name,
    String email,
    String address,
    String phone   
) {

}


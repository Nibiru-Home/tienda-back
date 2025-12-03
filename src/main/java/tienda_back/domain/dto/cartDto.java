package tienda_back.domain.dto;

import tienda_back.domain.model.user;

import java.util.Date;

public record cartDto(
        Long id,
        Float total,
        Float price,
        Date date,
        String status,
        user user
) {
}

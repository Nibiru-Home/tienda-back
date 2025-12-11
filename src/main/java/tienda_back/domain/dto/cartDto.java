package tienda_back.domain.dto;

import java.util.Date;

public record CartDto(
                Long id,
                Float total,
                Float price,
                Date date,
                String status,
        UserRegisterDto user) {
}

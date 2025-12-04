package tienda_back.domain.dto;

import tienda_back.domain.model.User;

import java.util.Date;

public record CartDto(
                Long id,
                Float total,
                Float price,
                Date date,
                String status,
                User user) {
}

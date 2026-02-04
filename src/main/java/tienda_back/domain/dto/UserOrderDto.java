package tienda_back.domain.dto;

import java.io.Serializable;
import java.util.Date;

public record UserOrderDto(
        String id,
        UserDto user,
        Double total,
        Date date,
        String status,
        CartDto cart) implements Serializable {
}

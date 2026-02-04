package tienda_back.controller.webmodel.response;

import tienda_back.domain.dto.CartDto;


import java.util.Date;

public record UserOrderResponse(
                String id,
                UserResponse user,
                Double total,
                Date date,
                String status,
                CartDto cart 
) {
}

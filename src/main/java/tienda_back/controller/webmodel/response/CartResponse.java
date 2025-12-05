package tienda_back.controller.webmodel.response;

import java.util.Date;

public record CartResponse(
                Long id,
                Float total,
                Float price,
                Date date,
                String status,
                UserResponse user) {

}
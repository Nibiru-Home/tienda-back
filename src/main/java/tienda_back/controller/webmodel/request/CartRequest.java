package tienda_back.controller.webmodel.request;

import java.util.Date;

public record CartRequest(
        Long id,
        Float total,
        Float price,
        Date date,
        String status,
        UserRequest user) {

}

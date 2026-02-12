package tienda_back.infraestructura.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PagoTarjeta(
        Autorizacion autorizacion,
        Origen origen,
        Destino destino,
        Pago pago) {

    public record Autorizacion(
            String login,
            @JsonProperty("api_token") String apiToken) {
    }

    public record Origen(
            Long id,
            String number,
            String expirationDate,
            int cvv,
            String name) {
    }

    public record Destino(String iban) {
    }

    public record Pago(
            BigDecimal importe,
            String concepto) {
    }
}

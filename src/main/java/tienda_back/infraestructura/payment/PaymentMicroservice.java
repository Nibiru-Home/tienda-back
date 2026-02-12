package tienda_back.infraestructura.payment;

import tienda_back.infraestructura.payment.model.PagoTarjeta;

public interface PaymentMicroservice {
    void payment(PagoTarjeta request);
}

package tienda_back.domain.service;

import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;

public interface PaymentCheckoutService {
    CheckoutPaymentResultDto checkout(CheckoutPaymentDto request);
}

package tienda_back.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tienda_back.controller.webmodel.request.CheckoutPaymentRequest;
import tienda_back.controller.webmodel.response.CheckoutPaymentResponse;
import tienda_back.domain.dto.CheckoutPaymentDto;
import tienda_back.domain.dto.CheckoutPaymentResultDto;
import tienda_back.domain.service.PaymentCheckoutService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentCheckoutService paymentCheckoutService;

    public PaymentController(PaymentCheckoutService paymentCheckoutService) {
        this.paymentCheckoutService = paymentCheckoutService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutPaymentResponse> checkout(@Valid @RequestBody CheckoutPaymentRequest request,
            jakarta.servlet.http.HttpServletRequest httpRequest) {
        java.util.UUID userId = (java.util.UUID) httpRequest.getAttribute("USER_ID");
        CheckoutPaymentResultDto result = paymentCheckoutService.checkout(new CheckoutPaymentDto(
                userId.toString(),
                request.cardNumber(),
                request.expirationMonth(),
                request.cvv(),
                request.cardHolder()));

        return ResponseEntity.ok(new CheckoutPaymentResponse(result.message(), result.amount()));
    }
}

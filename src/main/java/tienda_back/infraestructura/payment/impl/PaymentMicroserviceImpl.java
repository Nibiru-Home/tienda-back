package tienda_back.infraestructura.payment.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tienda_back.domain.exception.BusinessException;
import tienda_back.infraestructura.payment.PaymentMicroservice;
import tienda_back.infraestructura.payment.model.PagoTarjeta;

@Component
public class PaymentMicroserviceImpl implements PaymentMicroservice {

    private final RestTemplate restTemplate;
    private final String bankBaseUrl;

    public PaymentMicroserviceImpl(
            RestTemplate restTemplate,
            @Value("${bank.integration.base-url:http://localhost:8081}") String bankBaseUrl) {
        this.restTemplate = restTemplate;
        this.bankBaseUrl = normalizeBaseUrl(bankBaseUrl);
    }

    @Override
    public void payment(PagoTarjeta request) {
        try {
            restTemplate.postForEntity(bankBaseUrl + "/api/pagoTarjeta", request, Void.class);
        } catch (RestClientException ex) {
            throw new BusinessException("No se pudo conectar con el banco: " + ex.getMessage());
        }
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new BusinessException("La configuracion del banco no es valida (base URL).");
        }
        return baseUrl.trim().replaceAll("/+$", "");
    }
}

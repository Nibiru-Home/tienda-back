package tienda_back.domain.service;

import java.util.UUID;
import tienda_back.domain.model.Token;

public interface TokenService {

    Token generate(UUID userId);

    boolean validate(String tokenValue);

    UUID extractUserId(String tokenValue);
}

package tienda_back.domain.repository;

import java.util.Optional;
import tienda_back.domain.model.Token;

public interface TokenRepository {

    void save(Token token);
    Optional<Token> findByValue(String value);
}
package tienda_back.config.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import tienda_back.domain.service.TokenService;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class UserFilter implements Filter {

    private final TokenService tokenService;

    public UserFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");
        String token = null;

        if (header != null) {
            if (header.startsWith("Bearer ")) {
                token = header.substring(7);
            } else {
                token = header;
            }
        }

        if (token != null && tokenService.validate(token)) {
            UUID userId = tokenService.extractUserId(token);
            req.setAttribute("USER_ID", userId);
        }

        chain.doFilter(request, response);
    }
}

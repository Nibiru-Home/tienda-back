package tienda_back.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import tienda_back.domain.service.TokenService;

import java.io.IOException;
import java.util.UUID;

public class UserFilter implements Filter {

    private final TokenService tokenService;

    public UserFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenService.validate(token)) {
                UUID userId = tokenService.extractUserId(token);
                request.setAttribute("USER_ID", userId);
            }
        }

        chain.doFilter(request, response);
    }
}

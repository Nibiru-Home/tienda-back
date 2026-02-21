package tienda_back.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tienda_back.config.filter.AuthFilter;
import tienda_back.config.filter.UserFilter;
import tienda_back.domain.service.TokenService;

@Configuration
public class FilterConfig {

    private final TokenService tokenService;

    public FilterConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public FilterRegistrationBean<UserFilter> userFilterRegistration() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter(tokenService));
        registrationBean.addUrlPatterns("/api/payments/checkout");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/api/payments/checkout");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}

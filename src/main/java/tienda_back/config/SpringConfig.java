package tienda_back.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tienda_back.domain.repository.*;
import tienda_back.domain.service.*;
import tienda_back.domain.service.impl.*;
import tienda_back.persistence.dao.jpa.*;
import tienda_back.persistence.dao.jpa.impl.*;
import tienda_back.persistence.repository.impl.*;

@Configuration
@EnableJpaRepositories(basePackages = "tienda_back.persistence.dao.jpa")
@EntityScan(basePackages = "tienda_back.persistence.dao.jpa.entity")
public class SpringConfig {

    // JPA DAOs
    @Bean
    public CartJpaDao cartJpaDao() {
        return new CartJpaDaoImpl();
    }

    @Bean
    public CartProductJpaDao cartProductJpaDao() {
        return new CartProductJpaDaoImpl();
    }

    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }

    @Bean
    public TokenJpaDao tokenJpaDao() {
        return new TokenJpaDaoImpl();
    }

    @Bean
    public UserJpaDao userJpaDao() {
        return new UserJpaDaoImpl();
    }

    // Repositories
    @Bean
    public CartRepository cartRepository(CartJpaDao cartJpaDao) {
        return new CartRepositoryImpl(cartJpaDao);
    }

    @Bean
    public CartProductRepository cartProductRepository(CartProductJpaDao cartProductJpaDao) {
        return new CartProductRepositoryImpl(cartProductJpaDao);
    }

    @Bean
    public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao) {
        return new CategoryRepositoryImpl(categoryJpaDao);
    }

    @Bean
    public ProductRepository productRepository(ProductJpaDao productJpaDao) {
        return new ProductRepositoryImpl(productJpaDao);
    }

    @Bean
    public TokenRepository tokenRepository(TokenJpaDao tokenJpaDao) {
        return new TokenRepositoryImpl(tokenJpaDao);
    }

    @Bean
    public UserRepository userRepository(UserJpaDao userJpaDao) {
        return new UserRepositoryImpl(userJpaDao);
    }

    // Services
    @Bean
    public CartService cartService(CartRepository cartRepository, UserService userService) {
        return new CartServiceImpl(cartRepository, userService);
    }

    @Bean
    public CartProductService cartProductService(CartProductRepository cartProductRepository,
            CartRepository cartRepository,
            ProductRepository productRepository) {
        return new CartProductServiceImpl(cartProductRepository, cartRepository, productRepository);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }

    @Bean
    public TokenService tokenService(TokenRepository tokenRepository) {
        return new TokenServiceImpl(tokenRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

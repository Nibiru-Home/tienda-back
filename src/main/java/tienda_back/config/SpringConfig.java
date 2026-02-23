package tienda_back.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import tienda_back.chat.OpenAiChatService;
import tienda_back.infraestructura.payment.PaymentMicroservice;

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

    @Bean
    public UserOrderJpaDao userOrderJpaDao() {
        return new UserOrderJpaDaoImpl();
    }

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

    @Bean
    public UserOrderRepository userOrderRepository(UserOrderJpaDao userOrderJpaDao) {
        return new UserOrderRepositoryImpl(userOrderJpaDao);
    }

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
    public UserOrderService userOrderService(UserOrderRepository userOrderRepository,
            CartRepository cartRepository) {
        return new UserOrderServiceImpl(userOrderRepository, cartRepository);
    }

    @Bean
    public PaymentCheckoutService paymentCheckoutService(
            CartService cartService,
            CartProductService cartProductService,
            UserOrderService userOrderService,
            PaymentMicroservice paymentMicroservice,
            @Value("${bank.integration.login:Marta}") String bankLogin,
            @Value("${bank.integration.api-token:token1}") String bankApiToken,
            @Value("${bank.integration.destination-iban:ES33 0081 5220 0001 2345 6789}") String destinationIban,
            @Value("${bank.integration.concept:Compra Nibiru Home}") String paymentConcept,
            @Value("${checkout.shipping-fee:6.99}") double shippingFee,
            @Value("${checkout.free-shipping-threshold:80}") double freeShippingThreshold) {
        return new PaymentCheckoutServiceImpl(
                cartService, cartProductService, userOrderService, paymentMicroservice,
                bankLogin, bankApiToken, destinationIban, paymentConcept, shippingFee, freeShippingThreshold);
    }

    @Bean
    public OpenAiChatService openAiChatService(
            ObjectMapper objectMapper,
            @Value("${openai.api-key:}") String apiKey,
            @Value("${openai.model:gpt-4.1-mini}") String model) {
        return new OpenAiChatService(objectMapper, apiKey, model);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

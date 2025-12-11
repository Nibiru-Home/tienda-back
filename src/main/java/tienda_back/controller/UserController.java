package tienda_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda_back.controller.mapper.UserMapper;
import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.model.User;
import tienda_back.domain.service.UserService;
import tienda_back.domain.service.TokenService;
import tienda_back.controller.webmodel.request.LoginRequest;
import tienda_back.controller.webmodel.request.RegisterRequest;
import tienda_back.controller.webmodel.response.AuthResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    private final UserMapper mapper = UserMapper.getInstance();

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        UserRegisterDto dto = mapper.toRegisterDto(request);

        userService.register(dto);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        UserLoginDto dto = mapper.toLoginDto(request);

        User user = userService.login(dto);

        var token = tokenService.generate(user.getId());

        AuthResponse response = mapper.toAuthResponse(user, token.getValue());

        return ResponseEntity.ok(response);
    }
}

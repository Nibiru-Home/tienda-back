package tienda_back.domain.service;

import java.util.List;
import java.util.UUID;
import tienda_back.domain.model.User;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.dto.UserLoginDto;

public interface UserService {
    List<User> getAll();
    User getById(UUID id);
    User create(User user);
    User update(User user);
    void deleteById(UUID id);
    User getByEmail(String email);
    User login(UserLoginDto userLoginDto);
    void register(UserRegisterDto userRegisterDto);
}

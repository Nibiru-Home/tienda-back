package tienda_back.domain.service.impl;

import java.util.List;
import java.util.UUID;

import tienda_back.domain.dto.UserLoginDto;
import tienda_back.domain.dto.UserRegisterDto;
import tienda_back.domain.exception.LoginFailedException;
import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.exception.UserAlreadyExistsException;
import tienda_back.domain.mapper.UserMapper;
import tienda_back.domain.model.User;
import tienda_back.domain.repository.UserRepository;
import tienda_back.domain.service.UserService;
import tienda_back.domain.validation.DtoValidator;
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = UserMapper.getInstance();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con el id: " + id + " no existe"));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        UUID id = user.getId();
        if (id == null || !userRepository.existsById(id)) {
            throw new ResourceNotFoundException("El usuario con el id: " + id + " no existe");
        }
        return userRepository.update(user);
    }

    @Override
    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("El usuario con el id: " + id + " no existe");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new LoginFailedException("El usuario no se ha encontrado"));
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.email())
                .orElseThrow(() -> new LoginFailedException("El usuario no existe"));
        if (!userLoginDto.password().equals(user.getPassword())) {
            throw new LoginFailedException("La contraseña no es correcta");
        }
        return user;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        DtoValidator.validate(userRegisterDto);
        if (userRepository.existsByEmail(userRegisterDto.email())) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        String password = userRegisterDto.password();
        User user = userMapper.fromRegister(userRegisterDto, password);

        userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

}

package tienda_back.domain.service.impl;

import java.util.List;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.User;
import tienda_back.domain.respository.UserRepository;
import tienda_back.domain.service.UserService;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario con el id: " + id + " no existe"));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario con el id: " + id + " no existe"));
        userRepository.deleteById(id);
    }
}

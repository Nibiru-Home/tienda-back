package tienda_back.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.User;
import tienda_back.domain.repository.UserRepository;
import tienda_back.persistence.dao.jpa.UserJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;
import tienda_back.persistence.repository.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaDao userJpaDao;

    public UserRepositoryImpl(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public List<User> findAll() {
        return userJpaDao.findAll(0, 1000).stream()
                .map(UserMapper.getInstance()::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaDao.findById(id).map(UserMapper.getInstance()::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaDao.findByEmail(email).map(UserMapper.getInstance()::toUser);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserMapper.getInstance().toUserJpaEntity(user);
        if (user.getId() == null) {
            return UserMapper.getInstance().toUser(userJpaDao.insert(entity));
        } else {
            return UserMapper.getInstance().toUser(userJpaDao.update(entity));
        }
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return userJpaDao.findById(id).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaDao.findByEmail(email).isPresent();
    }

}

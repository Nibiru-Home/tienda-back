package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.User;
import tienda_back.persistence.dao.jpa.entity.UserJpaEntity;

public class UserMapper {

    private static UserMapper INSTANCE;

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }

    public User toUser(UserJpaEntity userJpaEntity) {
        if (userJpaEntity == null) {
            return null;
        }

        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getEmail(),
                userJpaEntity.getPassword(),
                userJpaEntity.getAddress(),
                userJpaEntity.getPhone(),
                userJpaEntity.getRole());
    }

    public UserJpaEntity toUserJpaEntity(User user) {
        if (user == null) {
            return null;
        }

        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(user.getId());
        userJpaEntity.setName(user.getName());
        userJpaEntity.setEmail(user.getEmail());
        userJpaEntity.setPassword(user.getPassword());
        userJpaEntity.setAddress(user.getAddress());
        userJpaEntity.setPhone(user.getPhone());
        userJpaEntity.setRole(user.getRole());
        return userJpaEntity;
    }
}

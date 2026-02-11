package tienda_back.persistence.repository.impl;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.User;
import tienda_back.domain.model.UserOrder;
import tienda_back.domain.repository.UserOrderRepository;
import tienda_back.persistence.dao.jpa.UserOrderJpaDao;
import tienda_back.persistence.dao.jpa.entity.UserOrderJpaEntity;

import tienda_back.persistence.repository.mapper.UserOrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserOrderRepositoryImpl implements UserOrderRepository {

    private final UserOrderJpaDao userOrderJpaDao;
    private final UserOrderMapper mapper = UserOrderMapper.getInstance();

    public UserOrderRepositoryImpl(UserOrderJpaDao userOrderJpaDao) {
        this.userOrderJpaDao = userOrderJpaDao;
    }

    @Override
    public UserOrder save(UserOrder userOrder) {
        UserOrderJpaEntity jpaEntity = mapper.toJpaEntity(userOrder);
        UserOrderJpaEntity savedEntity = userOrderJpaDao.insert(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UserOrder> findById(String id) {
        return userOrderJpaDao.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<UserOrder> findAll() {
        return userOrderJpaDao.findAll(1, 1000).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrder> findByUser(User user) {
        return userOrderJpaDao.findAll(1, 1000).stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        userOrderJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return userOrderJpaDao.findById(id).isPresent();
    }

    @Override
    public UserOrder update(UserOrder userOrder) {
        UserOrderJpaEntity entity = mapper.toJpaEntity(userOrder);
        return mapper.toDomain(userOrderJpaDao.update(entity));
    }
}

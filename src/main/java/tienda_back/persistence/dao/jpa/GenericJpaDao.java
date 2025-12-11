package tienda_back.persistence.dao.jpa;

import java.util.List;
import java.util.Optional;

public interface GenericJpaDao<T, ID> {
    List<T> findAll(int page, int size);

    Optional<T> findById(ID id);

    T insert(T jpaEntity);

    T update(T jpaEntity);

    void deleteById(ID id);
}

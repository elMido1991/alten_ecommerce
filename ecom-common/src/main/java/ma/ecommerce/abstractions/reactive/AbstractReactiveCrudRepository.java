package ma.ecommerce.abstractions.reactive;

import ma.ecommerce.abstractions.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface AbstractReactiveCrudRepository<ENTITY extends AbstractEntity<ID>, ID> extends ReactiveCrudRepository<ENTITY, ID> {
}
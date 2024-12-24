package ma.ecommerce.abstractions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractCrudRepository<ENTITY extends AbstractEntity<ID>, ID> extends JpaRepository<ENTITY, ID> {
}

package pl.ug.citycourier.internal.security.internal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ug.citycourier.internal.security.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}

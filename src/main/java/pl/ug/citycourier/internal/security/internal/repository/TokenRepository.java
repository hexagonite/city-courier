package pl.ug.citycourier.internal.security.internal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ug.citycourier.internal.security.entity.VerificationToken;

@Repository
public interface TokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}

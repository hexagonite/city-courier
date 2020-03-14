package pl.ug.citycourier.internal.coordinate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinatePairRepository extends CrudRepository<CoordinatePair, Long> {
    Optional<CoordinatePair> findByLatitudeEqualsAndLongitudeEquals(double latitude, double longitude);
}

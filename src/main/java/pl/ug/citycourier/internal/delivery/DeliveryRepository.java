package pl.ug.citycourier.internal.delivery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ug.citycourier.internal.pack.Pack;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Optional<Delivery> findByPack(Pack pack);

    List<Delivery> findByCourierIsNull();
}

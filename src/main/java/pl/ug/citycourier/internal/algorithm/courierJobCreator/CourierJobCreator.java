package pl.ug.citycourier.internal.algorithm.courierJobCreator;

import pl.ug.citycourier.internal.algorithm.dto.CourierInAlgorithm;
import pl.ug.citycourier.internal.courier.CourierJob;

import java.util.List;
import java.util.Queue;

public interface CourierJobCreator {

    Queue<CourierJob> createCourierJobs(List<CourierInAlgorithm> couriers);

}

package pl.ug.citycourier.internal.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourierTaskManager {

    private Map<String, Collection<CourierTask>> tasksForAllCouriers = new HashMap<>();

    public Collection<CourierTask> getAndRemoveTasksForCourier(String courierName) {
        Collection<CourierTask> tasksForSingleCourier = tasksForAllCouriers.getOrDefault(courierName, Collections.emptyList());
        tasksForAllCouriers.put(courierName, Collections.emptyList());
        return tasksForSingleCourier;
    }

    public void addNewTaskForCourier(String courierName, CourierTask courierTask) {
        if (tasksForAllCouriers.containsKey(courierName)) {
            tasksForAllCouriers.get(courierName).add(courierTask);
        } else {
            tasksForAllCouriers.put(courierName, Arrays.asList(courierTask));
        }
    }

}

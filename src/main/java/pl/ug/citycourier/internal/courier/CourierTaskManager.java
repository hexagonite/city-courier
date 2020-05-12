package pl.ug.citycourier.internal.courier;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourierTaskManager {

    private Map<String, Collection<CourierTask>> tasksForAllCouriers = new HashMap<>();

    public Collection<CourierTask> getAndRemoveTasksForCourier(String courierName) {
        Collection<CourierTask> tasksForSingleCourier = tasksForAllCouriers.getOrDefault(courierName, Collections.emptyList());
        tasksForAllCouriers.put(courierName, new ArrayList<>());
        return tasksForSingleCourier;
    }

    public void addNewTaskForCourier(String courierName, CourierTask courierTask) {
        if (tasksForAllCouriers.containsKey(courierName)) {
            tasksForAllCouriers.get(courierName).add(courierTask);
        } else {
            List<CourierTask> tasks = new ArrayList<>();
            tasks.add(courierTask);
            tasksForAllCouriers.put(courierName, tasks);
        }
    }

}

package pl.ug.citycourier.internal.courier;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourierTaskManagerTest {

    @InjectMocks
    private CourierTaskManager testee;

    @Test
    public void getAndRemoveTasksForCourier() {
        String courierName = "courier";
        CourierTask courierTask = new CourierTask();

        testee.addNewTaskForCourier(courierName, courierTask);

        Collection<CourierTask> tasks = testee.getAndRemoveTasksForCourier(courierName);

        assertThat(tasks).isNotEmpty();
        assertThat(tasks.size()).isOne();
        assertThat(tasks.iterator().next()).isEqualTo(courierTask);
    }


    @Test
    public void getAndRemoveTasksForCourier_noTasksForCourier() {
        String courierName = "courier";

        Collection<CourierTask> tasks = testee.getAndRemoveTasksForCourier(courierName);

        assertThat(tasks).isEmpty();
    }

}

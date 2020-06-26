package pl.ug.citycourier.internal.courier;

import pl.ug.citycourier.internal.user.User;

import java.util.Queue;

public class CourierJob {
    private Queue<CourierTask> tasks;
    private User courier;

    public CourierJob(Queue<CourierTask> tasks, User courier) {
        this.tasks = tasks;
        this.courier = courier;
    }

    public Queue<CourierTask> getTasks() {
        return tasks;
    }

    public void setTasks(Queue<CourierTask> tasks) {
        this.tasks = tasks;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }
}

package Utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Model.TaskEntity;

public class QueueManager {
    private static final BlockingQueue<TaskEntity> taskQueue = new LinkedBlockingQueue<>();

    public static void addTask(TaskEntity task) {
        taskQueue.add(task);
    }

    public static TaskEntity getTask() throws InterruptedException {
        return taskQueue.take();
    }
}

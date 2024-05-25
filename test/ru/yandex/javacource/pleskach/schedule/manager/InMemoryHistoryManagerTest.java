package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Assertions;
import ru.yandex.javacource.pleskach.schedule.manager.HistoryManager;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Task;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void savingPastVersionOfData() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task(1, "Task1", Status.NEW, "descriptionTask1");
        taskManager.createTask(task1);
        taskManager.getTask(task1.getId());
        Assertions.assertEquals(task1, taskManager.getHistory().getFirst());
        Task task2 = new Task(task1.getId(), "Task1Update", Status.NEW, "descriptionTask1Update");
        taskManager.updateTask(task2);
        Assertions.assertEquals(task1, taskManager.getHistory().getFirst());
    }

}
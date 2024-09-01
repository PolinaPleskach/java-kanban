package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Task;

class InMemoryHistoryManagerTest {

    @Test
    void savingPastVersionOfData() throws InvalidInputException {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        taskManager.createTask(task1);
        taskManager.getTask(task1.getId());
        Assertions.assertEquals(task1, taskManager.getHistory().getFirst());
        Task task2 = new Task(task1.getId(), "Task1Update", Status.NEW, "descriptionTask1Update");
        taskManager.updateTask(task2);
        Assertions.assertEquals(task1, taskManager.getHistory().getFirst());
    }

}
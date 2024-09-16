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

        historyManager.add(task1);

        Task task2 = new Task(2, "Task2", Status.NEW, "descriptionTask2");
        taskManager.createTask(task2);

        historyManager.add(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(historyManager.getHistory().getFirst(), task1);
        assertEquals(historyManager.getHistory().get(1), task2);
        assertEquals(historyManager.getHistory().size(),2);

        assertEquals(historyManager.getHistory().getFirst().getStatus(), task1.getStatus());

    }
}
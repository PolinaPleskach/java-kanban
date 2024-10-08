package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void addDifferentTypesOfTasks() throws InvalidInputException {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task1);
        Epic epic1 = new Epic(2, "Эпик 1", "Описание эпика 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 9, 15, 0, 0));
        taskManager.createTask(epic1);
        Subtask subtask1 = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.NEW, LocalDateTime.of(2025, 9, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES), 2);
        taskManager.createTask(subtask1);

        assertEquals(3, taskManager.getAllTasks().size());

        assertEquals(task1, taskManager.getTask(1));
        assertEquals(epic1, taskManager.getTask(2));
        assertEquals(subtask1, taskManager.getTask(3));
    }

    @Test
    void idDidNotConflicted() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task1);
        Task task2 = new Task(2, "Задача 1", "Описание задачи 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2025, 9, 17, 7, 0));
        taskManager.createTask(task2);
        task2.setId(1);
        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void immutabilityOfTasks() throws InvalidInputException {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task);
        Task task2 = taskManager.getTask(task.getId());
        assertEquals(task, task2);
    }

}
package ru.yandex.javacource.pleskach.schedule.task;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class TaskTest {

    TaskManager taskManager = Managers.getDefault();
    @Test
    public void taskEqual() {
        Task taskOne = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW, Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        Task taskTwo = new Task(2, "Задача 2", "Описание задачи 3", Status.NEW, Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        Assertions.assertNotEquals(taskOne, taskTwo);
    }

    @Test
    void addNewTask() throws InvalidInputException {
        Task task = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW, Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task);
        final int taskId = task.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }
}
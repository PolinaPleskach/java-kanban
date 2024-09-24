package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SubtaskTest {
    @Test
    public void subtaskEqual() throws InvalidInputException {
        TaskManager taskManager = new Managers().getDefault();
        Epic epicOne = new Epic(1, "Эпик 1", "Описание эпика 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 9, 15, 0, 0));
        taskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES),2);
        Subtask subtaskTwo = new Subtask(4, "Подзадача 2", "Описание подзадачи 2", Status.NEW,
                LocalDateTime.of(2024, 9, 16, 0, 0), Duration.of(11, ChronoUnit.MINUTES),2);
        Assertions.assertEquals(subtaskOne, subtaskTwo);
    }

}
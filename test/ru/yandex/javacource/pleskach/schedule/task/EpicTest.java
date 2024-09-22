package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {
    @Test
    public void epicEqual() {
        Epic epicOne = new Epic(1, "Эпик 1", "Описание эпика 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024,10,15, 0, 0));
        Epic epicTwo = new Epic(1, "Эпик 1", "Описание эпика 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024,10,15, 0, 0));
        Assertions.assertEquals(epicOne, epicTwo);
    }
}
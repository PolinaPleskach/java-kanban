package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;

class EpicTest {
    @Test
    public void epicEqual() throws InvalidInputException {
        Epic epicOne = new Epic(1, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        Epic epicTwo = new Epic(1, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        Assertions.assertEquals(epicOne, epicTwo);
    }
}
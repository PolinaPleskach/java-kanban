package ru.yandex.javacource.pleskach.schedule.test.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;

class EpicTest {
    @Test
    public void epicEqual() {
        Epic epicOne = new Epic(1, "epic1", Status.NEW, "description1");
        Epic epicTwo = new Epic(1, "epic1", Status.NEW, "description1");
        Assertions.assertEquals(epicOne, epicTwo);
    }
}
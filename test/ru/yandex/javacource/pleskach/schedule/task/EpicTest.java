package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class EpicTest {
    @Test
    public void epicEqual() {
sprint_7-solution-in-file-manager
        Epic epicOne = new Epic(1, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        Epic epicTwo = new Epic(1, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");

        Assertions.assertEquals(epicOne, epicTwo);
    }
}
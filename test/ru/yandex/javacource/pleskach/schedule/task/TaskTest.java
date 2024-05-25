package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Task;

class TaskTest {
    @Test
    public void taskEqual() {
        Task taskOne = new Task(1, "task1", Status.NEW, "description1");
        Task taskTwo = new Task(2, "task2", Status.NEW, "description2");
        Assertions.assertNotEquals(taskOne, taskTwo);
    }
}
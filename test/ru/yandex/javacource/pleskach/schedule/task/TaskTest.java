package ru.yandex.javacource.pleskach.schedule.task;


import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;


class TaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    public void taskEqual()  {
        Task taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        Task taskTwo = new Task(2, "Задача 2", Status.NEW, "Описание задачи 2");
        Assertions.assertNotEquals(taskOne, taskTwo);
    }
}
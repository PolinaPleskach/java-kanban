package ru.yandex.javacource.pleskach.schedule.task;


import org.junit.jupiter.api.Test;
sprint_7-solution-in-file-manager



class TaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
sprint_7-solution-in-file-manager
    public void taskEqual()  {
        Task taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        Task taskTwo = new Task(2, "Задача 2", Status.NEW, "Описание задачи 2");
        Assertions.assertNotEquals(taskOne, taskTwo);
    }
}
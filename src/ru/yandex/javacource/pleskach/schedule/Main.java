package ru.yandex.javacource.pleskach.schedule;

import ru.yandex.javacource.eskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.*;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task taskOne = new Task(1, "Lala", Status.NEW, "lflf");
        int task1 = taskManager.createTask(taskOne);
        System.out.println(task1);
        Task taskTwo = new Task(1, "pipipi", Status.NEW, "kkuu");
        int taskTwoId = taskManager.createTask(taskTwo);

        Epic epicOne = new Epic(1, "красный", Status.IN_PROGRESS, "hrgrgr");
        int epicOneId = taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(1,"lala",Status.NEW,"pupupu", 1);
        int subtaskOneId = taskManager.createSubTask(subtaskOne);


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getSubtasks(subtaskOneId));
        System.out.println(taskManager.getEpicSubtasks(epicOneId));
        System.out.println(taskManager.getTask(taskTwoId));
        System.out.println(taskManager.getTask(3));
    }
}

package ru.yandex.javacource.pleskach.schedule;

import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task taskOne = new Task(1, "Lala", Status.NEW, "lflf");
        int task1 = manager.createTask(taskOne);
        System.out.println(task1);
        Task taskTwo = new Task(1, "pipipi", Status.NEW, "kkuu");
        int taskTwoId = manager.createTask(taskTwo);

        Epic epicOne = new Epic(1, "красный", Status.IN_PROGRESS, "hrgrgr");
        int epicOneId = manager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(1,"lala",Status.NEW,"pupupu", 1);
        int subtaskOneId = manager.createSubTask(subtaskOne);


        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getSubtasks(subtaskOneId));
        System.out.println(manager.getEpicSubtasks(epicOneId));
        System.out.println(manager.getTask(taskTwoId));
        System.out.println(manager.getTask(3));

        manager.addHistory();

        System.out.println("История задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("История эпиков:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println(task);
            }
        }
        System.out.println("История подзадач:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}

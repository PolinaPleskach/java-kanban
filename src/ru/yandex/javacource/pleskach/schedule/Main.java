package ru.yandex.javacource.pleskach.schedule;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) throws InvalidInputException, IOException {
        TaskManager manager =  Managers.getDefaultFileBackedTaskManager();
        Task taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        int task1 = manager.createTask(taskOne);

        Epic epicOne = new Epic(3, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        int epicOneId = manager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1",3,Status.NEW,4);
        int subtaskOneId = manager.createSubtask(subtaskOne);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getSubtasks(subtaskOneId));
        System.out.println(manager.getEpic(epicOneId));

        System.out.println(manager.getTask(task1));
        System.out.println(manager.getSubtasks(subtaskOneId));

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
      TaskManager load = FileBackedTaskManager.loadFromFile(new File ("./java-kanban.csv"));
      //System.out.println("");
    }
}
package ru.yandex.javacource.pleskach.schedule;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) throws InvalidInputException, IOException {
        TaskManager manager = Managers.getDefaultFileBackedTaskManager();
        Task taskOne = new Task(1, "Задача 1", "Описание задачи 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        int task1 = manager.createTask(taskOne);

        Epic epicOne = new Epic(2, "Эпик 1", "Описание эпика 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024,10,15, 0, 0));
        int epicOneId = manager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(3, "Подзадача 1", "Описание подзадачи 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES),2);

        int subtaskOneId = manager.createSubtask(subtaskOne);

        Subtask subtaskTwo = new Subtask(4, "Подзадача 2", "Описание подзадачи 2", Status.NEW,
                LocalDateTime.of(2024, 9, 16, 0, 0), Duration.of(11, ChronoUnit.MINUTES),2);
        manager.createSubtask(subtaskTwo);
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
        }
        System.out.println("История подзадач:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        TaskManager load = FileBackedTaskManager.loadFromFile(new File("./java-kanban.csv"));
        System.out.println(load);
        System.out.println("Вывод задач в порядке приоритета");
        manager.getPrecedenceTasks().forEach( System.out::println);

    }
}
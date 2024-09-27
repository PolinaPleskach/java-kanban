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
        TaskManager taskManager = Managers.getDefaultFileBackedTaskManager();
        Task taskOne = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        int task1 = taskManager.createTask(taskOne);
        

        Epic epicOne = new Epic(2, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024,10,15, 0, 0));
        int epicOneId = taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(3, "Subtask 1", "Description 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES),2);

        int subtaskOneId = taskManager.createSubtask(subtaskOne);

        Subtask subtaskTwo = new Subtask(4, "Subtask 2", "Description 2", Status.NEW,
                LocalDateTime.of(2024, 9, 16, 0, 0), Duration.of(11, ChronoUnit.MINUTES),2);
        taskManager.createSubtask(subtaskTwo);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getSubtasks(subtaskOneId));
        System.out.println(taskManager.getEpic(epicOneId));
        System.out.println(taskManager.getTask(task1));
        System.out.println(taskManager.getSubtasks(subtaskOneId));

        System.out.println("История задач:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("История эпиков:");
        for (Task epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        System.out.println("История подзадач:");
        for (Task subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
        TaskManager load = FileBackedTaskManager.loadFromFile(new File("./java-kanban.csv"));
        System.out.println(load);
        System.out.println("Вывод задач в порядке приоритета");
        taskManager.getPrecedenceTasks().forEach( System.out::println);
    }
}
package ru.yandex.javacource.pleskach.schedule.http;

import com.sun.net.httpserver.HttpServer;
import ru.yandex.javacource.pleskach.schedule.http.handler.*;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final int port;
    private HttpServer server;
    private final TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) {
        this(taskManager, PORT);
    }

    public HttpTaskServer(TaskManager taskManager, int port) {
        this.taskManager = taskManager;
        this.port = port;
    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefaultFileBackedTaskManager();

        Task taskOne = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(taskOne);

        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();

        Epic epicOne = new Epic(2, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(3, "Subtask 1", "Description 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES), 2);
        taskManager.createSubtask(subtaskOne);
        taskManager.getHistory();

    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress("localHost", PORT), 0);
            server.start();
            System.out.println("Сервер запущен. Порт №: " + PORT);
            this.server.createContext("/tasks", new TaskHandler(taskManager));
            this.server.createContext("/epics", new EpicHandler(taskManager));
            this.server.createContext("/subtasks", new SubtaskHandler(taskManager));
            this.server.createContext("/history", new HistoryHandler(taskManager));
            this.server.createContext("/precedence", new PrecedenceHandler(taskManager));

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void stop() {
        server.stop(0);
        System.out.println("Сервер остановлен. Порт №: " + PORT);
    }
}
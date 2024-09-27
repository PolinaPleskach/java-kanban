package ru.yandex.javacource.pleskach.schedule.http;


import ru.yandex.javacource.pleskach.schedule.http.handler.BaseHttpHandler;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class HttpTaskManagerTasksTest {

    TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
    Gson gson = BaseHttpHandler.getGson();

    public HttpTaskManagerTasksTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        taskManager.deleteTasks();
        taskManager.deleteSubtasks();
        taskManager.deleteEpics();
        httpTaskServer.start();
    }

    @AfterEach
    public void shutDown() {
        httpTaskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {//work
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task);
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());


        List<Task> tasksFromManager = taskManager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Task 1", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testGetTask() throws IOException, InterruptedException {//work
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = taskManager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Task 1", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {//work
        Task taskOne = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + taskOne.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(204, response.statusCode());

        List<Task> tasksFromManager = taskManager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(0, tasksFromManager.size(), "Некорректное количество задач");
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epicOne = new Epic(1, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2025, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(2, "Subtask 1", "Description 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES), 1);
        taskManager.createSubtask(subtaskOne);
        String subtaskJson = gson.toJson(subtaskOne);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Subtask> tasksFromManager = taskManager.getAllSubtasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Subtask 1", tasksFromManager.get(0).getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testGetSubtask() throws IOException, InterruptedException {
        Epic epicOne = new Epic(1, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2025, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask(2, "Subtask 1", "Description 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES), 1);
        taskManager.createSubtask(subtaskOne);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Subtask> tasksFromManager = taskManager.getAllSubtasks();

        assertNotNull(tasksFromManager, "Подадачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество подзадач");
        assertEquals("Subtask 1", tasksFromManager.get(0).getTitle(), "Некорректное имя подзадачи");
    }

    @Test
    public void testDeleteSubtask() throws IOException, InterruptedException {//work

        Epic epicOne = new Epic(1, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2025, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask(2, "Subtask 1", "Description 1", Status.NEW,
                LocalDateTime.of(2025, 10, 15, 0, 0), Duration.of(15, ChronoUnit.MINUTES), 1);
        taskManager.createSubtask(subtaskOne);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/" + subtaskOne.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, response.statusCode());

        List<Subtask> tasksFromManager = taskManager.getAllSubtasks();

        assertNotNull(tasksFromManager, "Подадачи не возвращаются");
        assertEquals(0, tasksFromManager.size(), "Некорректное количество подзадач");
    }

    @Test
    public void testGetEpic() throws IOException, InterruptedException {//work
        Epic epicOne = new Epic(1, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);

        Epic epicTwo = new Epic(2, "Epic 2", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 10, 15, 0, 0));
        taskManager.createEpic(epicTwo);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> tasksFromManager = taskManager.getAllEpics();

        assertNotNull(tasksFromManager, "Подадачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество эпиков");
        assertEquals("Epic 2", tasksFromManager.get(1).getTitle(), "Некорректное имя эпика");
    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {//work
        Epic epicOne = new Epic(1, "Epic 1", "Description 1", Duration.of(15, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 10, 15, 0, 0));
        taskManager.createEpic(epicOne);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/" + epicOne.getId());
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, response.statusCode());

        List<Epic> tasksFromManager = taskManager.getAllEpics();

        assertNotNull(tasksFromManager, "Подадачи не возвращаются");
        assertEquals(0, tasksFromManager.size(), "Некорректное количество подзадач");
    }

    @Test
    void getPrecedenceTask() throws IOException, InterruptedException {//work
        Task taskOne = new Task(1, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2024, 9, 17, 7, 0));
        taskManager.createTask(taskOne);
        Task taskTwo = new Task(2, "Task 1", "Description 1", Status.NEW,
                Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.of(2025, 9, 17, 7, 0));
        taskManager.createTask(taskTwo);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/precedence");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        List<Task> expectedTasks = taskManager.getPrecedenceTasks();
        assertEquals(expectedTasks.size(), 2);
    }
}
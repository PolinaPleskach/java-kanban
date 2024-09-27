package ru.yandex.javacource.pleskach.schedule.http.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.exception.HttpNotFoundException;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler extends BaseHttpHandler {

    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Начало обработки подзадачи");
        try (exchange) {

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String[] pathParts = path.split("/");
            switch (method) {
                case "GET":
                    try {
                        if (pathParts.length == 2) {
                            writeResponse(exchange, getGson().toJson(taskManager.getAllSubtasks()), 200);
                        } else if (pathParts.length == 3) {
                            writeResponse(exchange, getGson().toJson(taskManager.getSubtasks(getIdFromPath(pathParts[2]))), 200);
                        }
                    } catch (InvalidInputException e) {
                        writeResponse(exchange, "Ошибка при чтении тела запроса", 400);
                    }
                    break;
                case "POST":
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Subtask subTask = getGson().fromJson(body, Subtask.class);
                    try {
                        taskManager.getSubtasks(subTask.getId());
                        taskManager.updateSubtask(subTask);
                        writeResponse(exchange, "Обновление подзадачи выполнено", 201);
                    } catch (HttpNotFoundException e) {
                        taskManager.createSubtask(subTask);
                        writeResponse(exchange, "Создана подзадача", 201);
                    } catch (InvalidInputException e) {
                        writeResponse(exchange, "Ошибка при чтении тела запроса", 400);
                    }
                    break;
                case "DELETE":
                    int id = getIdFromPath(pathParts[2]);
                    if (id == -1) {
                        writeResponse(exchange, "Данных с таким номером не существует", 404);
                    } else {
                        taskManager.deleteSubtask(id);
                        sendResponseCode(exchange);
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            writeResponse(exchange, "Ошибка ввода/вывода", 500);
        }
    }
}
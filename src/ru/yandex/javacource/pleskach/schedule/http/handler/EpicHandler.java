package ru.yandex.javacource.pleskach.schedule.http.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.exception.HttpNotFoundException;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Начало обработки эпика");
        try (exchange) {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String[] pathParts = path.split("/");
            switch (method) {
                case "GET":
                    try {
                        if (pathParts.length == 2) {
                            writeResponse(exchange, getGson().toJson(taskManager.getAllEpics()), 200);

                        } else if (pathParts.length == 3) {

                            writeResponse(exchange, getGson().toJson(taskManager.getEpic(getIdFromPath(pathParts[2]))), 200);
                        } else if (pathParts.length > 3) {

                            writeResponse(exchange, getGson().toJson(taskManager.getEpicSubtasks(taskManager.getEpic(getIdFromPath(pathParts[2])))), 200);
                        }
                    } catch (InvalidInputException e) {
                        writeResponse(exchange, "Ошибка при чтении тела запроса", 400);
                    }
                    break;
                case "POST":
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Epic epic = getGson().fromJson(body, Epic.class);
                    if (epic.getSubtaskIds() == null) {
                        epic.setSubtaskIds(new ArrayList<>());
                    }
                    try {
                        taskManager.getEpic(epic.getId());
                    } catch (HttpNotFoundException e) {
                        taskManager.createEpic(epic);
                        writeResponse(exchange, "Создание эпика успешно", 201);
                    } catch (InvalidInputException e) {
                        writeResponse(exchange, "Ошибка при чтении тела запроса", 400);
                    }

                    break;
                case "DELETE":
                    try {
                        int id = getIdFromPath(pathParts[2]);
                        if (id == -1) {
                            writeResponse(exchange, "Данного эпика не существует", 404);
                        } else {
                            Epic epic2 = taskManager.getEpic(id);
                            List<Subtask> listSubtasks = taskManager.getEpicSubtasks(epic2);
                            listSubtasks.clear();
                            taskManager.deleteEpic(id);
                            sendResponseCode(exchange);
                        }
                    } catch (InvalidInputException e) {
                        writeResponse(exchange, "Ошибка при чтении тела запроса", 400);
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            writeResponse(exchange, "Ошибка ввода/вывода", 500);
        }
    }
}
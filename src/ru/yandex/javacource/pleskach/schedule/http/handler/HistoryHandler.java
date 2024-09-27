package ru.yandex.javacource.pleskach.schedule.http.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.pleskach.schedule.exception.HttpHandlerException;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) {
        try (exchange) {
            try {
                if (exchange.getRequestMethod().equals("GET")) {
                    writeResponse(exchange, getGson().toJson(taskManager.getHistory()), 200);
                } else
                    writeResponse(exchange, "Ошибка при обработке запроса", 404);
            } catch (IOException e) {
                throw new HttpHandlerException("Ошибка ввода/вывода при обработке запроса", e);
            }
        }
    }
}
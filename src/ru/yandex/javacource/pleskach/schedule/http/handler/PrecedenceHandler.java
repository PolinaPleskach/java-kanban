package ru.yandex.javacource.pleskach.schedule.http.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import java.io.IOException;

public class PrecedenceHandler extends BaseHttpHandler {
    public PrecedenceHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                writeResponse(exchange, getGson().toJson(taskManager.getPrecedenceTasks()), 200);
            } else
                writeResponse(exchange, "Ошибка при обработке запроса", 400);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            writeResponse(exchange, "Ошибка ввода/вывода", 500);
        }
    }
}
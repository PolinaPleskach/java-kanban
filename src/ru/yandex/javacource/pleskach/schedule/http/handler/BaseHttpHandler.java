package ru.yandex.javacource.pleskach.schedule.http.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacource.pleskach.schedule.exception.HttpNotFoundException;
import ru.yandex.javacource.pleskach.schedule.exception.HttpValidationException;
import ru.yandex.javacource.pleskach.schedule.http.adapter.DurationAdapter;
import ru.yandex.javacource.pleskach.schedule.http.adapter.LocalDateTimeAdapter;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class BaseHttpHandler implements HttpHandler {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected final TaskManager taskManager;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    protected void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        byte[] resp = responseString.getBytes(DEFAULT_CHARSET);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter());
        return gsonBuilder.create();
    }

    protected void sendResponseCode(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    protected Optional<Integer> getId(String pathPart) {
        int id = Integer.parseInt(pathPart);
        if (id == -1) {
            throw new HttpNotFoundException("Нет данных с id: " + id);
        } else {
            return Optional.of(id);
        }
    }

    protected int getIdFromPath(String pathPart) {
        Optional<Integer> idFromPath = getId(pathPart);
        return idFromPath.orElse(-1);
    }

    protected void exception(HttpExchange exchange, Exception exception) {
        try {
            switch (exception) {
                case JsonSyntaxException e -> writeResponse(exchange, "HTTP_FORMAT_ERROR", 400);
                case HttpNotFoundException e -> writeResponse(exchange, "HTTP_NOT_FOUND_ERROR", 404);
                case HttpValidationException e -> writeResponse(exchange, "HTTP_VALIDATION_ERROR", 406);
                case null, default -> {
                    assert exception != null;
                    exception.printStackTrace(System.out);
                    writeResponse(exchange, HttpError.HTTP_INTERNAL_ERROR.toString(), 500);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
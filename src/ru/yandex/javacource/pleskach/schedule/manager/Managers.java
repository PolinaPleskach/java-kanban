package ru.yandex.javacource.pleskach.schedule.manager;

import java.io.File;
import java.nio.file.Path;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFileBackedTaskManager() {
        return new FileBackedTaskManager(new File ("./java-kanban.csv"));
    }
}
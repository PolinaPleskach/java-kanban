package ru.yandex.javacource.pleskach.schedule.manager;

import java.nio.file.Path;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTaskManager getDefaultFileBackedTaskManager(Path file, Path fileToHistory) {
        return new FileBackedTaskManager(file, fileToHistory);
    }
}

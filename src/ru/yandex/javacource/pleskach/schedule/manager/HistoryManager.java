package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
sprint_7-solution-in-file-manager

    List<Task> getHistory();
}
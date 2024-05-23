package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history;
    private static final int MAX_HISTORY = 10;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if ((task != null)) {
            history.add(task);
            if (history.size() > MAX_HISTORY) {
                history.removeFirst();
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
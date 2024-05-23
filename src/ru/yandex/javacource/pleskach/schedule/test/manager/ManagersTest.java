package ru.yandex.javacource.pleskach.schedule.test.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.manager.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {
    @Test
    void defaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager);
        assertInstanceOf(InMemoryHistoryManager.class, historyManager);
    }

    @Test
    void defaultTask() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
        assertInstanceOf(InMemoryTaskManager.class, taskManager);
    }
}
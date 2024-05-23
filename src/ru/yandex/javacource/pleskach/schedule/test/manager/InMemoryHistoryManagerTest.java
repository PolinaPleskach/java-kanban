package ru.yandex.javacource.pleskach.schedule.test.manager;

import ru.yandex.javacource.pleskach.schedule.manager.HistoryManager;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Task;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void SavingPastVersionOfData() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task(1, "Task1", Status.NEW, "descriptionTask1" );
        taskManager.createTask(task1);
        historyManager.add(task1);
        assertEquals(historyManager.getHistory().getFirst(), task1);
        assertEquals(historyManager.getHistory().getFirst().getStatus(), task1.getStatus());
    }
}
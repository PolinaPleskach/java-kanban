package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Task;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    @Test
    void SavingPreviousVersionAndDataWithoutRepetitions() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task(1,"Task 1", "Description 1", Status.NEW, Duration.of(10, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 8, 21, 11, 30));
        taskManager.createTask(task1);

        historyManager.add(task1);

        Task task2 = new Task(2,"Task 2", "Description 2", Status.NEW, Duration.of(10, ChronoUnit.MINUTES),
                LocalDateTime.of(2024, 9, 21, 11, 46));
        taskManager.createTask(task2);

        historyManager.add(task2);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(historyManager.getHistory().getFirst(), task1);
        assertEquals(historyManager.getHistory().get(1), task2);
        assertEquals(historyManager.getHistory().size(),2);

        assertEquals(historyManager.getHistory().getFirst().getStatus(), task1.getStatus());

    }
}
package ru.yandex.javacource.pleskach.schedule.manager;
import ru.yandex.javacource.pleskach.schedule.task.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;




class InMemoryHistoryManagerTest {

    @Test
    void SavingPreviousVersionAndDataWithoutRepetitions() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task(1, "Task1", Status.NEW, "descriptionTask1");
        taskManager.createTask(task1);

        historyManager.add(task1);

        Task task2 = new Task(2, "Task2", Status.NEW, "descriptionTask2");
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
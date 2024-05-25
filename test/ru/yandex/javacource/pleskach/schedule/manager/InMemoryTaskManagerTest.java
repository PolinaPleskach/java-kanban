package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Managers.getDefault();

    @Test
    void addDifferentTypesOfTasks()  {
        Task task1 = new Task(1,"addTask1", Status.NEW,"addDescription1");
        taskManager.createTask(task1);
        Epic epic1 = new Epic(1,"addEpic1", Status.NEW, "addDescription1");
        taskManager.createTask(epic1);
        Subtask subtask1 = new Subtask(1,"addSubtask1", Status.DONE,"addDescription1", 1);
        taskManager.createTask(subtask1);

        assertEquals(3, taskManager.getAllTasks().size());

        assertEquals(task1, taskManager.getTask(1));
        assertEquals(epic1, taskManager.getTask(2));
        assertEquals(subtask1, taskManager.getTask(3));
    }

    @Test
    void idDidNotConflicted(){
        Task task1 = new Task(1,"addTask1", Status.NEW,"addDescription1");
        taskManager.createTask(task1);
        Task task2 = new Task(2,"addTask2", Status.NEW,"addDescription2");
        taskManager.createTask(task2);
        task2.setId(1);
        assertEquals(2,taskManager.getAllTasks().size());
    }

    @Test
    void immutabilityOfTasks() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task(1,"addTask1", Status.NEW,"addDescription1");
        taskManager.createTask(task);
        Task task2 = taskManager.getTask(task.getId());

        assertEquals(task, task2);
    }

}
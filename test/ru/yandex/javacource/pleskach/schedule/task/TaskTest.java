package ru.yandex.javacource.pleskach.schedule.task;


import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class TaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTask(){
        Task task = new Task(1,"task1", Status.NEW, "lala" );
        taskManager.createTask(task);
        final int taskId = task.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не существует.");
        assertEquals(task, savedTask, "Несовпадение задач");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Несовпадение задач");
    }
}
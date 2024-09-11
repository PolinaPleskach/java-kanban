package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileBackedTaskManagerTest that = (FileBackedTaskManagerTest) o;
        return Objects.equals(manager, that.manager) && Objects.equals(fileBackedTaskManager, that.fileBackedTaskManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager, fileBackedTaskManager);
    }

    private TaskManager manager;
    private FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    void createFile() {
        manager = Managers.getDefaultFileBackedTaskManager();
        Task taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        manager.createTask(taskOne);
        Epic epicOne = new Epic(2, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        manager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1", 2, Status.NEW, 3);
        manager.createSubtask(subtaskOne);

    }

    @Test
    void loadFromFile() throws IOException {
        fileBackedTaskManager = FileBackedTaskManager.loadFromFile(new File("./java-kanban.csv"));
        assertEquals(1, fileBackedTaskManager.getAllTasks().size(), "Количество задач после выгрузки из файла не совпадает");
        manager.getAllTasks().equals(fileBackedTaskManager.getAllTasks());
        assertEquals(1, fileBackedTaskManager.getAllEpics().size(), "Количество эпиков после выгрузки из файла не совпадает");
        manager.getAllEpics().equals(fileBackedTaskManager.getAllEpics());
        assertEquals(1, fileBackedTaskManager.getAllSubtasks().size(), "Количество подзадач после выгрузки из файла не совпадает");
        manager.getAllSubtasks().equals(fileBackedTaskManager.getAllSubtasks());
    }
}
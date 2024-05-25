package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int createTask(Task task);

    void updateTask(Task task);

    Task getTask(int id);

    List<Task> getAllTasks();

    void deleteTask(int id);

    void removeAllTasks();

    int createEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic getEpic(int id);

    List<Epic> getAllEpics();

    void deleteEpic(int id);

    void removeAllEpics();

    Integer createSubTask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    Subtask getSubtasks(int id);

    List<Subtask> getAllSubtasks();

    void deleteSubtask(int id);

    void deleteSubtasks();

    List<Subtask> getEpicSubtasks(int epicId);

    List<Task> getHistory();
}

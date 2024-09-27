package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int createTask(Task task);

    void updateTask(Task task);

    Task getTask(int id) throws InvalidInputException;

    List<Task> getAllTasks();

    void deleteTask(int id) throws InvalidInputException;

    void deleteTasks();

    int createEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic getEpic(int id) throws InvalidInputException;

    List<Epic> getAllEpics();

    void deleteEpic(int id) throws InvalidInputException;

    void deleteEpics();

    int createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    Subtask getSubtasks(int id) throws InvalidInputException;

    List<Subtask> getAllSubtasks();

    void deleteSubtask(int id);

    void deleteSubtasks();

    ArrayList<Subtask> getEpicSubtasks(Epic epic);

    List<Task> getHistory();

    List<Task> getPrecedenceTasks();

}
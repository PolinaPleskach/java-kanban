package ru.yandex.javacource.Pleskach.schedule;

import ru.yandex.javacource.Pleskach.schedule.Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int generatorId;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;

    public TaskManager() {
        generatorId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public int createTask(Task task) {
        int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    public Object getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return "Такой задачи не существует";
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void removeAllTasks() {
        tasks.clear();
    }


    public void createEpic(Epic epic) {
        epic.setId(++generatorId);
        epic.setStatus(Status.NEW);
        epics.put(generatorId, epic);

    }

    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        savedEpic.setTitle(epic.getTitle());
        savedEpic.setDescription(epic.getDescription());
    }

    public Object getEpicById(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            return "Такого эпика не существует";
        }
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Integer createSubTask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        int id = ++generatorId;
        subtask.setId(id);
        subtasks.put(id, subtask);
        subtask.getEpic().getSubtaskIds().add(id);
        updateEpicStatus(epic);
        return id;
    }

    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtasks.put(id, subtask);
        updateEpicStatus(epic);
    }

    public Object getSubtasksById(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            return "Такой задачи не существует";
        }
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtaskIds().remove((Integer) id);
        updateEpicStatus(epic);
    }

    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds(); //стоит сделать метод
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }

    private void updateEpicStatus(Epic epic) {

        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer epicSubtaskId : epic.getSubtaskIds()) {
            Status status = subtasks.get(epicSubtaskId).getStatus();
            if (!status.equals(Status.NEW)) {
                allNew = false;
            }
            if (!status.equals(Status.DONE)) {
                allDone = false;
            }
        }
        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
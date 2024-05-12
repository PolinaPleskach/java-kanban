package manager;

import realizationModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int id;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        id = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public void createTask(Task task) {
        task.setId(++id);
        tasks.put(id, task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task getTaskById(int id) throws InputException {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else throw new InputException("Такой задачи не существует");
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeTaskById(int id) throws InputException {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            throw new InputException("Такой задачи не существует");
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }


    public void createEpic(Epic epic) {
        epic.setId(++id);
        epic.setStatus(Status.NEW);
        epics.put(id, epic);
    }

    public void updateEpic(Epic epic) {
        epic.setEpicSubtasks(epics.get(epic.getId()).getEpicSubtasks());
        epics.put(epic.getId(), epic);
        checkStatus(epic);
    }

    public Epic getEpicById(int id) throws InputException {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            throw new InputException("Такого эпика не существует");
        }
    }

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }

    public void removeEpicById(int id) throws InputException {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            for (Integer subtaskId : epic.getEpicSubtasks()) {
                subtasks.remove(subtaskId);
            }
            epic.setEpicSubtasks(new ArrayList<>());
        } else {
            throw new InputException("Такого эпика не существует");
        }
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void createSubTask(Subtask subtask) {
        subtask.setId(++id);
        subtasks.put(id, subtask);
        subtask.getEpic().getEpicSubtasks().add(id);
        checkStatus(subtask.getEpic());

    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        checkStatus(subtask.getEpic());
    }

    public Subtask getSubtasksById(int id) throws InputException {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            throw new InputException("Такой задачи не существует");
        }
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    public void removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            Epic epic = subtasks.get(id).getEpic();
            epic.getEpicSubtasks().remove((Integer) id);
            checkStatus(epic);
            subtasks.remove(id);
        }
    }

    public void removeAllSubtask() {
        ArrayList<Epic> epicsForSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            subtask.getEpic().setEpicSubtasks(new ArrayList<>());
            if (!epicsForSubtasks.contains(subtask.getEpic())) {
                epicsForSubtasks.add(subtask.getEpic());
            }
        }
        subtasks.clear();
        for (Epic epic : epicsForSubtasks) {
            epic.setStatus(Status.NEW);
        }
    }

    private void checkStatus(Epic epic) {

        if (epic.getEpicSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer epicSubtaskId : epic.getEpicSubtasks()) {
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
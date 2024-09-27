package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.exception.ManagerSaveException;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected  static int generatorId;
    protected  Map<Integer, Task> tasks;
    protected  Map<Integer, Epic> epics;
    protected  Map<Integer, Subtask> subtasks;
    protected  HistoryManager historyManager;
    private final TreeSet<Task> precedenceTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparing(Task::getId));

    public InMemoryTaskManager() {
        generatorId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public int createTask(Task task) {
        task.setEndTime(task.getStartTime().plus(task.getDuration()));
        int id = ++generatorId;
        task.setId(id);
        checkTasksIntersections(task);
        tasks.put(id, task);
        precedenceTasks.add(task);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }


    @Override
    public Task getTask(int id) throws InvalidInputException {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTask(int id) throws InvalidInputException {
        precedenceTasks.remove(tasks.get(id));
        tasks.remove(id);
    }

    @Override
    public void deleteTasks() {
        precedenceTasks.removeIf(task -> tasks.containsKey(task.getId()));
        tasks.clear();
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setEndTime(epic.getStartTime().plus(epic.getDuration()));
        int id = ++generatorId;
        epic.setStatus(Status.NEW);
        epics.put(id, epic);
        precedenceTasks.add(epic);
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        epic.setSubtaskIds(savedEpic.getSubtaskIds());
        epic.setStatus(savedEpic.getStatus());
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public Epic getEpic(int id) throws InvalidInputException {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpic(int id) throws InvalidInputException {
        final Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }


    @Override
    public int createSubtask(Subtask subtask) {
        subtask.setEndTime(subtask.getStartTime().plus(subtask.getDuration()));
        int id = ++generatorId;
        subtask.setId(id);
        subtasks.put(id, subtask);
        final Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtaskIds().add(id);
        updateEpicStatus(epic);
        precedenceTasks.add(subtask);
        return id;
    }

    @Override
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

    @Override
    public Subtask getSubtasks(int id) throws InvalidInputException {
        final Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteSubtask(int id) {
        precedenceTasks.remove(subtasks.get(id));
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtaskIds().remove((Integer) id);
        updateEpicStatus(epic);
        subtasks.remove(id);
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds(); //стоит сделать метод
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        List<Integer> epicSubtasksId = epic.getSubtaskIds();
        ArrayList<Subtask> epicSubtask = new ArrayList<>();
        for (Integer subtaskIds : epicSubtasksId) {
            epicSubtask.add(subtasks.get(subtaskIds));
        }
        return epicSubtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrecedenceTasks() {
                return new ArrayList<>(precedenceTasks);
    }

    private void checkTasksIntersections(Task task) {
        for (Task t : precedenceTasks) {
            if (t.getId() == (task.getId())) {
                continue;
            }
            if ((t.getStartTime().isBefore(task.getStartTime()) && t.getEndTime().isAfter(task.getStartTime())) ||
                    (t.getStartTime().isAfter(task.getStartTime()) && t.getStartTime().isBefore(task.getEndTime())) ||
                    (t.getStartTime().equals(task.getStartTime()))) {
                throw new ManagerSaveException("\nПересечение новой задачи: \n" +
                        task.getTitle() + " " + task.getStartTime() + " " + task.getEndTime() +
                        "\nс задачей: \n" + t.getTitle() + " " + t.getStartTime() + " " + t.getEndTime());
            }
        }
    }

    private void updateEpicDuration(Epic epic) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        Duration duration = Duration.of(0, ChronoUnit.MINUTES);
        for (Integer subTaskId : epic.getSubtaskIds()) {
            Subtask subTask = subtasks.get(subTaskId);
            duration = duration.plus(Duration.between(subTask.getStartTime(), subTask.getEndTime()));
            if (start == null || subTask.getStartTime().isBefore(start)) {
                start = subtasks.get(subTaskId).getStartTime();
            }
            if (end == null || subTask.getEndTime().isAfter(end)) {
                end = subtasks.get(subTaskId).getEndTime();
            }
        }
        epic.setDuration(duration);
        epic.setStartTime(start);
        epic.setEndTime(end);
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
        updateEpicDuration(epic);
    }

}
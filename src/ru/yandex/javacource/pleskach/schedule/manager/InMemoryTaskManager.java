package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements  TaskManager {
        private int generatorId;
        private final Map<Integer, Task> tasks;
        private final Map<Integer, Epic> epics;
        private final Map<Integer, Subtask> subtasks;
        private final HistoryManager historyManager;

        public InMemoryTaskManager() {
            generatorId = 0;
            tasks = new HashMap<>();
            epics = new HashMap<>();
            subtasks = new HashMap<>();
            historyManager = Managers.getDefaultHistory();
        }

        @Override
        public int createTask(Task task) {
            int id = ++generatorId;
            task.setId(id);
            tasks.put(id, task);
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
        public Task getTask(int id) {
            return tasks.get(id);
        }

        @Override
        public List<Task> getAllTasks() {
            return new ArrayList<>(tasks.values());
        }

        @Override
        public void deleteTask(int id) {
            tasks.remove(id);
        }

        @Override
        public void removeAllTasks() {
            tasks.clear();
        }

        @Override
        public int createEpic(Epic epic) {
            int id = ++generatorId;
            epic.setId(id);
            epics.put(id, epic);
            return id;
        }

        @Override
        public void updateEpic(Epic epic) {
            Epic savedEpic = epics.get(epic.getId());
            if (savedEpic == null) {
                return;
            }
            savedEpic.setTitle(epic.getTitle());
            savedEpic.setDescription(epic.getDescription());
        }

        @Override
        public Epic getEpic(int id) {
            return epics.get(id);
        }

        @Override
        public List<Epic> getAllEpics() {
            return new ArrayList<>(epics.values());
        }

        @Override
        public void deleteEpic(int id) {
            final Epic epic = epics.remove(id);
            if (epic == null) {
                return;
            }
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }

        @Override
        public void removeAllEpics() {
            epics.clear();
            subtasks.clear();
        }

        @Override
        public Integer createSubTask(Subtask subtask) {
            int id = ++generatorId;
            subtask.setId(id);
            subtasks.put(subtask.getId(), subtask);
            return subtask.getId();
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
        public Subtask getSubtasks(int id) {
            return subtasks.get(id);
        }

        @Override
        public List<Subtask> getAllSubtasks() {
            return new ArrayList<>(subtasks.values());
        }

        @Override
        public void deleteSubtask(int id) {
            Subtask subtask = subtasks.remove(id);
            if (subtask == null) {
                return;
            }
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) id);
            updateEpicStatus(epic);
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
        public ArrayList<Subtask> getEpicSubtasks(int epicId) {
            ArrayList<Subtask> tasks = new ArrayList<>();
            Epic epic = epics.get(epicId);
            if (epic == null) {
                return tasks;
            }
            for (int id : epic.getSubtaskIds()) {
                tasks.add(subtasks.get(id));
            }
            return tasks;
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

        @Override
        public void addHistory() {
            for (Task task : tasks.values()) historyManager.add(task);
        }

        @Override
        public List<Task> getHistory() {
            return historyManager.getHistory();
        }
}


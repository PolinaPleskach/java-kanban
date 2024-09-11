package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.exception.ManagerSaveException;
import ru.yandex.javacource.pleskach.schedule.task.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File files;// сделать все в одно!

    private static final String HEADER = "id,type,title,status,description,epic";//сделано в константу

    public FileBackedTaskManager(File file) {
        this.files = file;
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(files, StandardCharsets.UTF_8))) {
            bufferedWriter.write(HEADER);
            bufferedWriter.newLine();
            addAllToFile(bufferedWriter);
            bufferedWriter.newLine();
            saveHistory(bufferedWriter);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    void saveHistory(BufferedWriter bufferedWriter) throws ManagerSaveException, IOException {
        for (Task task : historyManager.getHistory()) {
            String str = task.getId() + ",,,,,";
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
    }

    private void addAllToFile(BufferedWriter bufferedWriter) throws IOException {//сделала отдельный метод для сохранения задач
        List<String> lines = new ArrayList<>();
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                lines.add(taskToString(task));
            }
        }

        if (!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                lines.add(taskToString(epic));
            }
        }

        if (!subtasks.isEmpty()) {
            for (Subtask subtask : subtasks.values()) {
                lines.add(taskToString(subtask));
            }
        }
        for (String str : lines) {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
    }

    private String taskToString(Task task) {
        return task.getId() + "," + task.getTaskType() + "," + task.getTitle() + "," + task.getStatus() + "," + task.getDescription();
    }

    private String taskToString(Epic epic) {
        return epic.getId() + "," + epic.getTaskType() + "," + epic.getTitle() + "," + epic.getStatus() + "," + epic.getDescription();
    }

    private String taskToString(Subtask subtask) {
        return subtask.getId() + "," + subtask.getTaskType() + "," + subtask.getTitle() + "," + subtask.getStatus() + "," + subtask.getDescription() + "," + subtask.getEpicId();
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();
            for (int i = 1; i < lines.size(); i++) {
                if (!lines.get(i).isEmpty()) {
                    String[] line = lines.get(i).split(",");
                    Task task = fromString(line);
                    switch (task.getTaskType()) {
                        case TASK -> manager.createTask(task);
                        case EPIC -> manager.createEpic((Epic) task);
                        case SUBTASK -> manager.createSubtask((Subtask) task);
                    }
                } else {
                    loadFromHistoryFile(lines.subList(i + 1, lines.size()), manager);
                    break;
                }
            }
        } catch (IOException ex) {
            throw new ManagerSaveException("Ошибка при выгрузке файла");
        }
        return manager;
    }

    public static void loadFromHistoryFile(List<String> lines, FileBackedTaskManager manager) throws IOException {// сделать восстановление
        try {
            for (String line1 : lines) {
                String[] line = line1.split(",");
                int taskId = Integer.parseInt(line[0]);
                Task taskFromFile = manager.getTask(taskId);
                Task epicFromFile = manager.getEpic(taskId);
                Task subtaskFromFile = manager.getSubtasks(taskId);
                if (taskFromFile != null) {
                    manager.historyManager.add(manager.getTask(taskFromFile.getId()));
                } else if (epicFromFile != null) {
                    manager.historyManager.add(manager.getEpic(epicFromFile.getId()));
                } else if (subtaskFromFile != null) {
                    manager.historyManager.add(manager.getSubtasks(subtaskFromFile.getId()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static Task fromString(String[] line) {
        int id = Integer.parseInt(line[0]);
        TaskTypes type = TaskTypes.valueOf(line[1]);
        String title = line[2];
        Status status = Status.valueOf(line[3]);
        String description = line[4];
        return switch (type) {
            case TASK -> new Task(id, title, description, status);
            case EPIC -> new Epic(id, title, description, status);
            case SUBTASK -> {
                int epicId = Integer.parseInt(line[5]);
                yield new Subtask(id, title, description, status, epicId);
            }
        };
    }


    @Override
    public int createTask(Task task) {
        int id = super.createTask(task);
        save();
        return id;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Task getTask(int id) throws InvalidInputException {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public void deleteTask(int id) throws InvalidInputException {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public int createEpic(Epic epic) {
        int id = super.createEpic(epic);
        save();
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public Epic getEpic(int id) throws InvalidInputException {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public void deleteEpic(int id) throws InvalidInputException {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public int createSubtask(Subtask subtask) {
        int id = super.createSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public Subtask getSubtasks(int id) throws InvalidInputException {
        Subtask subtask = super.getSubtasks(id);
        save();
        return subtask;
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }
}

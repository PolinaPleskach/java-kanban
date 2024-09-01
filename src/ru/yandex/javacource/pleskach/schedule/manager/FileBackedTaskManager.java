package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.exception.ManagerSaveException;
import ru.yandex.javacource.pleskach.schedule.task.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final Path filePathTask;
    private final Path filePathHistory;

    public FileBackedTaskManager(Path taskFilePath, Path historyFilePath) {
        super();
        this.filePathHistory = historyFilePath;
        this.filePathTask = taskFilePath;
    }

    public Path getTasksFilePath() {
        return filePathTask;
    }

    public Path getFilePathHistory() {
        return filePathHistory;
    }

    private void save() throws ManagerSaveException {
        List<String> lines = new ArrayList<>();
        try {
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
            FileWriter fileWriter = new FileWriter(filePathTask.toFile(), StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String header = "id,type,name,status,description,epic";
            bufferedWriter.write(header);
            bufferedWriter.newLine();
            for (String str : lines) {
                bufferedWriter.write(str + "\n");
            }

            bufferedWriter.close();

        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка создания записи");
        }
    }

    public String taskToString(Task task) {
        String str = task.getId() + "," + task.getTaskTypes() + "," + task.getTitle() + "," + task.getStatus() + "," + task.getDescription() + ",";
        if (task.getTaskTypes().equals(TaskTypes.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            str = str + subtask.getEpicId();
        }

        return str;
    }

    void saveHistory() throws ManagerSaveException {
        try {
            FileWriter fileWriter = new FileWriter(filePathHistory.toFile(), StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Task task : historyManager.getHistory()) {
                bufferedWriter.write(task.getId() + ",");
            }
            bufferedWriter.close();
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка создания записи");
        }
    }

    public void fromFile() throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader fileReader = new FileReader(filePathTask.toFile(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            lines.add(line);
        }
        bufferedReader.close();
        int max = 0;
        for (int i = 1; i < lines.size(); i++) {
            int curId = Integer.parseInt(lines.get(i).split(",")[0]);
            if (max < curId) {
                max = curId;
            }
            fromString(lines.get(i));
        }
        setId(max);
        for (Subtask subtask : subtasks.values()) {
            int epicId = subtask.getEpicId();
            Epic curEpic = epics.get(epicId);
            curEpic.getSubtaskIds().add(subtask.getId());
        }
    }

    public void fromHistoryFile() throws IOException {
        FileReader fileReader = new FileReader(filePathHistory.toFile(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] historyIds = new String[0];
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            historyIds = line.split(",");
        }
        bufferedReader.close();
        for (String id : historyIds) {
            int curId = Integer.parseInt(id);
            if (tasks.containsKey(curId)) {
                historyManager.add(tasks.get(curId));
            }
            if (epics.containsKey(curId)) {
                historyManager.add(epics.get(curId));
            }
            if (subtasks.containsKey(curId)) {
                historyManager.add(subtasks.get(curId));
            }
        }
    }

    private void fromString(String value) {
        String[] split = value.split(",");
        Task taskStr;

        switch (split[1]) {
            case "Task":
                taskStr = new Task(split[2], split[4], Status.valueOf(split[3]), Integer.parseInt(split[0]));
                tasks.put(Integer.parseInt(split[0]), taskStr);
                break;
            case "Epic":
                taskStr = new Epic(split[2], split[4], Integer.parseInt(split[0]));
                taskStr.setStatus(Status.valueOf(split[3]));
                epics.put(Integer.parseInt(split[0]), (Epic) taskStr);
                break;
            case "Subtask":
                taskStr = new Subtask(split[2], split[4], Integer.parseInt(split[0]), Status.valueOf(split[3]),
                        Integer.parseInt(split[5]));
                subtasks.put(Integer.parseInt(split[0]), (Subtask) taskStr);
                break;
            default:
                break;
        }
    }


    @Override
    public int createTask(Task task) {
        super.createTask(task);
        save();
        return generatorId;
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
        super.createEpic(epic);
        save();
        return 0;
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
    public Integer createSubTask(Subtask subtask) {
        super.createSubTask(subtask);
        save();
        return null;
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

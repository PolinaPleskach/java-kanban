package ru.yandex.javacource.pleskach.schedule.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.task.Epic;
import ru.yandex.javacource.pleskach.schedule.task.Status;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileBackedTaskManagerTest {

    public static FileBackedTaskManager fileBackedTaskManager;
    public static Task taskOne;
    public static Epic epicOne;
    public static Subtask subtaskOne;
    String history;

    @BeforeEach
    public void BeforeEach() throws IOException {
        File fileTest = File.createTempFile("fileTest", ".csv", new File("test"));
        File testHistoryFile = File.createTempFile("fileTestHistory", ".csv", new File("test"));
        Managers manager = new Managers();
        fileBackedTaskManager = manager.getDefaultFileBackedTaskManager(fileTest.toPath(), testHistoryFile.toPath());
        fileTest.deleteOnExit();
        testHistoryFile.deleteOnExit();
    }

    @Test
    public void loadFromHistoryFileTest() throws IOException, InvalidInputException {

        taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        fileBackedTaskManager.createTask(taskOne);
        epicOne = new Epic(1, "Эпик 1", Status.NEW, "Описание эпика 1");
        fileBackedTaskManager.createEpic(epicOne);
        subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        fileBackedTaskManager.createSubTask(subtaskOne);
        fileBackedTaskManager.getTask(taskOne.getId());
        fileBackedTaskManager.getSubtasks(subtaskOne.getId());

        List<Integer> checkHistory = new ArrayList<>();
        checkHistory.add(taskOne.getId());
        checkHistory.add(subtaskOne.getId());

        fileBackedTaskManager.fromHistoryFile();
        Assertions.assertNotNull(fileBackedTaskManager.getHistory());
        Assertions.assertEquals(checkHistory.size(), fileBackedTaskManager.getHistory().size());
    }

    @Test
    public void loadFromFileTest() throws IOException {
        fileBackedTaskManager.fromFile();
        Assertions.assertNotNull(fileBackedTaskManager.getAllTasks().size());
    }

    @Test
    public void saveTest() throws IOException, InvalidInputException {
        taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        fileBackedTaskManager.createTask(taskOne);
        epicOne = new Epic(1, "Эпик 1", Status.NEW, "Описание эпика 1");
        fileBackedTaskManager.createEpic(epicOne);
        subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        fileBackedTaskManager.createSubTask(subtaskOne);
        fileBackedTaskManager.getTask(taskOne.getId());
        fileBackedTaskManager.getSubtasks(subtaskOne.getId());
        List<String> sequenceOfNumberInFile = new ArrayList<>();
        FileReader fileReader = new FileReader(fileBackedTaskManager.getTasksFilePath().toFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            sequenceOfNumberInFile.add(line);
        }
        bufferedReader.close();

        List<String> sequenceOfNumber = new ArrayList<>();
        sequenceOfNumber.add("id,type,name,status,description,epic");
        sequenceOfNumber.add(taskOne.getId() + ",TASK" + "," + taskOne.getTitle() + "," + taskOne.getStatus() + "," +
                taskOne.getDescription() + ",");
        sequenceOfNumber.add(epicOne.getId() + ",EPIC" + "," + epicOne.getTitle() + "," + epicOne.getStatus() + "," +
                epicOne.getDescription() + ",");
        sequenceOfNumber.add(subtaskOne.getId() + ",SUBTASK" + "," + subtaskOne.getTitle() + "," + subtaskOne.getStatus() +
                "," + subtaskOne.getDescription() + "," + subtaskOne.getEpicId());
        Assertions.assertEquals(sequenceOfNumber, sequenceOfNumberInFile);
    }

    @Test
    public void saveHistoryTest() throws IOException, InvalidInputException {
        taskOne = new Task(1, "Задача 1", Status.NEW, "Описание задачи 1");
        fileBackedTaskManager.createTask(taskOne);
        epicOne = new Epic(1, "Эпик 1", Status.NEW, "Описание эпика 1");
        fileBackedTaskManager.createEpic(epicOne);
        subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1", 1, Status.NEW, 1);
        fileBackedTaskManager.createSubTask(subtaskOne);
        fileBackedTaskManager.getTask(taskOne.getId());
        fileBackedTaskManager.getSubtasks(subtaskOne.getId());
        fileBackedTaskManager.getTask(taskOne.getId());
        fileBackedTaskManager.getEpic(epicOne.getId());
        fileBackedTaskManager.getSubtasks(subtaskOne.getId());
        fileBackedTaskManager.saveHistory();
        fileBackedTaskManager.deleteTasks();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileBackedTaskManager.getFilePathHistory().toFile()));
        while (bufferedReader.ready()) {
            history = bufferedReader.readLine();
        }
        bufferedReader.close();

        String checkHistoryLine = taskOne.getId() + "," + epicOne.getId() + "," + subtaskOne.getId() + ",";

        Assertions.assertNotNull(history);
        Assertions.assertEquals(checkHistoryLine, history);
    }
}
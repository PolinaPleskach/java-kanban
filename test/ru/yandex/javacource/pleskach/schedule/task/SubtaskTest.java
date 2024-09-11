package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.exception.InvalidInputException;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
 sprint_7-solution-in-file-manager


import static org.junit.jupiter.api.Assertions.assertFalse;

class SubtaskTest {
    @Test
    public void subtaskEqual() throws InvalidInputException {
        TaskManager taskManager = new Managers().getDefault();
        Epic epicOne = new Epic(1, "Эпик 1", Status.IN_PROGRESS, "Описание эпика 1");
        taskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("Подзадача 1", "Описание подзадачи 1",1, Status.NEW,2);
        Subtask subtaskTwo = new Subtask("Подзадача 2", "Описание подзадачи 2",1, Status.NEW,3);
        Assertions.assertEquals(subtaskOne, subtaskTwo);
    }

}
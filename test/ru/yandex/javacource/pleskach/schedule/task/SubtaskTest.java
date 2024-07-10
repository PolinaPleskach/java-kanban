package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.pleskach.schedule.manager.Managers;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;
import ru.yandex.javacource.pleskach.schedule.task.Subtask;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SubtaskTest {
    @Test
    public void subtaskEqual() {
        TaskManager taskManager = new Managers().getDefault();
        Epic epicOne = new Epic(1, "epic1", Status.NEW, "description1");
        taskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask(1, "subtask1", Status.NEW, "description1", 1);
        Subtask subtaskTwo = new Subtask(1, "subtask2", Status.NEW, "description2", 1);
        Assertions.assertEquals(subtaskOne, subtaskTwo);
    }

}
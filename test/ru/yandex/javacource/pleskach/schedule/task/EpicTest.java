package ru.yandex.javacource.pleskach.schedule.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ru.yandex.javacource.pleskach.schedule.manager.TaskManager;

class EpicTest {
    @Test
    public void epicEqual() {
        Epic epicOne = new Epic(1, "epic1", Status.NEW, "description1");
        Epic epicTwo = new Epic(1, "epic1", Status.NEW, "description1");
        Assertions.assertEquals(epicOne, epicTwo);
    }
}
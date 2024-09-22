package ru.yandex.javacource.pleskach.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;



    public Subtask(int id, String title, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(id, title, description, status, startTime,duration);
        this.epicId = epicId;
        this.taskTypes = TaskTypes.SUBTASK;
    }
    public Subtask(int id, String title, String description, Status status, LocalDateTime startTime,LocalDateTime endTime, Duration duration, int epicId) {
        super(id, title, description, status, startTime,endTime,duration);
        this.epicId = epicId;
        this.taskTypes = TaskTypes.SUBTASK;
    }

    public TaskTypes getTaskType() {
        return TaskTypes.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "EpicId=" + epicId +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                "Duration: " + getDuration().toMinutes() + " mins, " +
                "StartTime: " + getStartTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
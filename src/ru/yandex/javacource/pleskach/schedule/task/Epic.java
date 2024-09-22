package ru.yandex.javacource.pleskach.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds;

    public Epic(int id, String title, String description, Duration duration, LocalDateTime startTime) {
        super(id, title, description, Status.NEW, duration, startTime);
        subtaskIds = new ArrayList<>();
        this.taskTypes = TaskTypes.EPIC;
    }

    public Epic(int id, String title, String description, Status status, LocalDateTime startTime, LocalDateTime endTime, Duration duration) {
        super(id, title, description, status, startTime,endTime, duration);
        subtaskIds = new ArrayList<>();
        this.taskTypes = TaskTypes.EPIC;
    }

    public TaskTypes getTaskType() {
        return TaskTypes.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> epicSubtasks) {
        this.subtaskIds = epicSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "EpicId=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return id == epic.id && Objects.equals(title, epic.title)
                && Objects.equals(description, epic.description)
                && status == epic.status && Objects.equals(subtaskIds, epic.subtaskIds);
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }
}
package ru.yandex.javacource.pleskach.schedule.task;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;


    public Subtask(String title, String description, int epicId, Status status, int id) {
        super(title, description, status, id, TaskTypes.SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(int id, String title, String description, Status status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public TaskTypes getTaskType() {
        return TaskTypes.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "EpicId=" + epicId +
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
        Subtask subtask = (Subtask) o;
        return id == subtask.id && Objects.equals(title, subtask.title)
                && Objects.equals(description, subtask.description)
                && status == subtask.status && epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

}

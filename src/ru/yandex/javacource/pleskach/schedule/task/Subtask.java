package ru.yandex.javacource.pleskach.schedule.task;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;


    public Subtask(String title, String description, int epicId, Status status, int id) {
        super(title, description, status, id, TaskTypes.SUBTASK);
        this.epicId = epicId;
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

    @Override
    public String toStringFromFile() {
        return String.format("%s,%s,%s,%s,%s,%s", id, taskTypes, title, status, description, getEpicId());
    }

}

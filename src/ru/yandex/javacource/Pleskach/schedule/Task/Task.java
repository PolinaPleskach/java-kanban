package ru.yandex.javacource.Pleskach.schedule.Task;

import java.util.Objects;

public class Task {
    protected int id;
    protected String title;
    protected String description;
    protected Status status;



    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ru.yandex.javacource.Pleskach.manager.Task{" +
                "idTask=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }
}
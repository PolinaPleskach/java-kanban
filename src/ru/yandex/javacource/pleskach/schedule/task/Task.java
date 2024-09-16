package ru.yandex.javacource.pleskach.schedule.task;

import java.util.Objects;

public class Task {
    protected int id;
    protected String title;
    protected String description;
    protected Status status;
    protected TaskTypes taskTypes;

    public Task(int id, String title, Status status, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.taskTypes = TaskTypes.TASK;
    }

    public Task(String title, String description, int id, TaskTypes taskTypes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskTypes = taskTypes;
    }

    public Task(String title, String description, Status status, int id, TaskTypes taskTypes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.taskTypes = taskTypes;
    }

    public Task(String title, String description, Status status, int id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description, TaskTypes taskTypes) {
        this.title = title;
        this.description = description;
        this.taskTypes = taskTypes;
    }

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(int id, String title, String description, Status status, TaskTypes taskTypes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.taskTypes = taskTypes;
    }

    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TaskTypes getTaskType() {
        return TaskTypes.TASK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public TaskTypes getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(TaskTypes taskTypes) {
        this.taskTypes = taskTypes;
    }

    @Override
    public String toString() {
        return "ru.yandex.javacource.Pleskach.ru.yandex.javacource.Pleskach.schedule.ru.yandex.javacource.pleskach.schedule.test.manager.Task{" +
                "idTask=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status);
    }
}
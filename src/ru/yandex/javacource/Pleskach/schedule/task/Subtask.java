package ru.yandex.javacource.Pleskach.schedule.task;

public class Subtask extends Task {
    private final int epicId;
    private Epic epic;


    public Subtask(String title, String description, Status status, Epic epic) {
        super(title, description, status);
        this.epicId = epic.id;
        this.epic = epic;
    }

    public Epic getEpic(){
        return epic;
    }

    public int getEpicId(){
        return epicId;
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
}
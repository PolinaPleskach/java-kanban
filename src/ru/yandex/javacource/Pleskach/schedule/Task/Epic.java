package ru.yandex.javacource.Pleskach.schedule.Task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds;

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        subtaskIds = new ArrayList<>();
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


    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }
}
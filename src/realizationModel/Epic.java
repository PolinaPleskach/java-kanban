package realizationModel;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> epicSubtask;
    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        epicSubtask = new ArrayList<>();
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtask;
    }

    public void setEpicSubtasks(ArrayList<Integer> epicSubtasks) {
        this.epicSubtask = epicSubtasks;
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


}
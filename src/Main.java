import realizationModel.*;
import manager.TaskManager;
public class Main {

    public static void main(String[] args) throws InputException {
        TaskManager taskManager = new TaskManager();

        Task taskOne = new Task("Погулять", "взять с собой кота", Status.NEW);
        taskManager.createTask(taskOne);
        Task taskTwo = new Task("Выпить", "чай", Status.IN_PROGRESS);
        taskManager.createTask(taskTwo);

        Epic epicOne = new Epic("Получить диплом", "красный");
        taskManager.createEpic(epicOne);

        Subtask subtaskOne = new Subtask("Заниматься", "пн-пт", Status.IN_PROGRESS,epicOne);
        taskManager.createSubTask(subtaskOne);

        Subtask subtaskTwo =  new Subtask("Делать все вовремя", "!!!",Status.IN_PROGRESS, epicOne);
        taskManager.createSubTask(subtaskTwo);

        Epic epicTwo = new Epic("Сделать генеральную уборку", "В субботу");
        taskManager.createEpic(epicTwo);

        Subtask subtaskThree = new Subtask("Разделить обязанности с парнем", "я - пылесосить, он - мыть пол", Status.IN_PROGRESS,epicTwo);
        taskManager.createSubTask(subtaskThree);


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getTaskById(4));
    }
}

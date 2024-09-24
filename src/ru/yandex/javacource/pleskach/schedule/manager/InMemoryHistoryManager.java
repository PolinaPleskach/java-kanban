package ru.yandex.javacource.pleskach.schedule.manager;

import ru.yandex.javacource.pleskach.schedule.task.Node;
import ru.yandex.javacource.pleskach.schedule.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> history;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    @Override
    public void remove(int id) {
        final Node<Task> node = history.remove(id);
        if (node == null) {
            return;
        }
        removeNode(node);
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            linkLast(task);
            history.put(task.getId(), tail);
        }
    }


    private void linkLast(Task element) {
        final Node<Task> pastTail = tail;
        final Node<Task> newNode = new Node<>(pastTail, element, null);
        tail = newNode;
        if (pastTail == null)
            head = newNode;
        else
            pastTail.next = newNode;
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> actualNode = head;
        while (actualNode != null) {
            tasks.add(actualNode.data);
            actualNode = actualNode.next;
        }
        return tasks;
    }

    private void removeNode(Node<Task> node) {
        final Node<Task> next = node.next;
        final Node<Task> prev = node.previous;
        node.data = null;
        if (tail == node && head == node) {

            tail = null;
            head = null;
        } else if (tail != node && head == node) {
            head = next;
            head.previous = null;
        } else if (tail == node && head != node) {
            tail = prev;
            tail.next = null;
        } else {
            prev.next = next;
            next.previous = prev;

        }
    }
}
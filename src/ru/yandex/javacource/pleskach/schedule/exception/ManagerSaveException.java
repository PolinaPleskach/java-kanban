package ru.yandex.javacource.pleskach.schedule.exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }
    public ManagerSaveException(final String message,Throwable ex){
        super(message,ex);
    }
}






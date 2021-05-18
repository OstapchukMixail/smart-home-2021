package ru.sbt.mipt.oop;
import ru.sbt.mipt.oop.Notifier;

public class NotifierImplementation implements Notifier {
    @Override
    public void send(String message) {
        System.out.println(message);
    }
}

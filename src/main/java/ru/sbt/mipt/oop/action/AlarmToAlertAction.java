package ru.sbt.mipt.oop.action;

import ru.sbt.mipt.oop.signaling.Alarm;

public class AlarmToAlertAction implements Action{
    @Override
    public void run(Object component) {
        if (component instanceof Alarm) {
            Alarm alarm = (Alarm) component;
            alarm.turnToAlert();
        }
    }
}

package ru.sbt.mipt.oop.action;

import ru.sbt.mipt.oop.signaling.Alarm;

public class ActivateDeactivateAlarmAction implements Action{
    private final boolean isToActivate;
    private final String code;

    public ActivateDeactivateAlarmAction(boolean isToActivate, String code) {
        this.isToActivate = isToActivate;
        this.code = code;
    }

    @Override
    public void run(Object component) {
        if (component instanceof Alarm) {
            Alarm alarm = (Alarm) component;
            if (isToActivate) {
                alarm.activate(code);
            } else  {
                alarm.deactivate(code);
            }
        }
    }
}

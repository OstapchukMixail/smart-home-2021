package ru.sbt.mipt.oop.signaling;

import ru.sbt.mipt.oop.AlertMessenger;

public class ActivatedState implements AlarmState {
    final Alarm alarm;

    public ActivatedState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate(String code) {}

    @Override
    public void deactivate(String code) {
        if (alarm.isCorrectCode(code)) {
            alarm.changeState(new DeactivatedState(alarm));
        } else {
            turnToAlert();
        }
    }

    @Override
    public void turnToAlert() {
        alarm.changeState(new AlertState(alarm));
        (new AlertMessenger()).send();
    }

    @Override
    public void react(AlarmReactor alarmReactor) {
        alarmReactor.onAlarmActiveState();
    }
}

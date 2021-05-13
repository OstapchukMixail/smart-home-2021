package ru.sbt.mipt.oop.signaling;

import ru.sbt.mipt.oop.AlertMessenger;

public class DeactivatedState implements AlarmState {
    final Alarm alarm;

    public DeactivatedState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate(String code) {
        if (alarm.isCorrectCode(code)) {
            alarm.changeState(new ActivatedState(alarm));
        }
    }

    @Override
    public void deactivate(String code) {}

    @Override
    public void turnToAlert() {
        alarm.changeState(new AlertState(alarm));
        (new AlertMessenger()).send();
    }

    @Override
    public void react(AlarmReactor alarmReactor) {
        alarmReactor.onAlarmInactiveState();
    }
}

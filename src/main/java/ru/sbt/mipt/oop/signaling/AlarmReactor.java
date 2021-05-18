package ru.sbt.mipt.oop.signaling;

public interface AlarmReactor {
    void onAlarmActiveState();
    void onAlarmInactiveState();
    void onAlarmAlertState();
}

package ru.sbt.mipt.oop.signaling;

interface AlarmState {
    void activate(String code);
    void deactivate(String code);
    void turnToAlert();
    void react(AlarmReactor alarmReactor);
}

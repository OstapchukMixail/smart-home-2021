package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.signaling.Alarm;

public class ActivateAlarmCommand implements Command {
    private final Alarm alarm;
    private final String code;

    public ActivateAlarmCommand(Alarm alarm, String code) {
        this.alarm = alarm;
        this.code = code;
    }

    @Override
    public void execute() {
        alarm.activate(code);
    }
}

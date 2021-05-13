package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.signaling.Alarm;

public class ToAlertCommand implements Command {
    private final Alarm alarm;

    public ToAlertCommand(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.turnToAlert();
    }
}

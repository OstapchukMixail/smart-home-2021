package ru.sbt.mipt.oop.remote.controllers;

import ru.sbt.mipt.oop.remote.controllers.commands.Command;

import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final Map<String, Command> buttonCodeToCommand;

    public RemoteControlImpl(Map<String, Command> buttonCodeToCommand) {
        this.buttonCodeToCommand = buttonCodeToCommand;
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        buttonCodeToCommand.get(buttonCode).execute();
    }
}

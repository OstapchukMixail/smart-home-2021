package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.action.AllLightAction;
import ru.sbt.mipt.oop.SmartHome;

public class TurnOnAllLightCommand implements Command{
    private final SmartHome smartHome;

    public TurnOnAllLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(new AllLightAction(true));
    }
}

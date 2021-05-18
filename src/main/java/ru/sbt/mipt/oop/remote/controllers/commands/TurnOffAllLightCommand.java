package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.action.AllLightAction;
import ru.sbt.mipt.oop.SmartHome;

public class TurnOffAllLightCommand implements Command{
    private final SmartHome smartHome;

    public TurnOffAllLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(new AllLightAction(false));
    }
}

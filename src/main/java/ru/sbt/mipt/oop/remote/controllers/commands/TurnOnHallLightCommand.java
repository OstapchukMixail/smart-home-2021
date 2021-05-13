package ru.sbt.mipt.oop.remote.controllers.commands;

import ru.sbt.mipt.oop.action.HallLightAction;
import ru.sbt.mipt.oop.SmartHome;

public class TurnOnHallLightCommand implements Command{
    private final SmartHome smartHome;

    public TurnOnHallLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }


    @Override
    public void execute() {
        smartHome.execute(new HallLightAction(false));
    }
}

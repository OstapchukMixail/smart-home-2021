package ru.sbt.mipt.oop.remote.controllers.commands;

import org.junit.Test;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TurnOnAllLightCommandTest {

    @Test
    public void execute() {
        Light lightId1 = new Light("1", false);
        Light lightId2 = new Light("2", true);
        Light lightId3 = new Light("3", false);
        Room kitchen = new Room(Arrays.asList(lightId1, lightId2),
                Arrays.asList(),
                "kitchen");
        Room hall = new Room(Arrays.asList(lightId3),
                Arrays.asList(),
                "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, hall));

        Command onAllLightCommand = new TurnOnAllLightCommand(smartHome);
        onAllLightCommand.execute();

        //verify
        assertTrue(lightId1.isOn());
        assertTrue(lightId2.isOn());
        assertTrue(lightId3.isOn());
    }
}

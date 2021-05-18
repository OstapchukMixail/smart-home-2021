package ru.sbt.mipt.oop.remote.controllers.commands;

import org.junit.Test;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TurnOnHallLightCommandTest {

    @Test
    public void execute() {
        Light lightId1 = new Light("1", false);
        Light lightId2 = new Light("2", true);
        Light lightId3 = new Light("3", false);
        Light lightId4 = new Light("4", true);
        Room kitchen = new Room(Arrays.asList(lightId1, lightId2),
                Arrays.asList(),
                "kitchen");
        Room hall = new Room(Arrays.asList(lightId3, lightId4),
                Arrays.asList(),
                "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, hall));

        Command onHallLightCommand = new TurnOnHallLightCommand(smartHome);
        onHallLightCommand.execute();

        //verify
        assertFalse(lightId1.isOn());
        assertTrue(lightId2.isOn());
        assertTrue(lightId3.isOn());
        assertTrue(lightId4.isOn());
    }
}

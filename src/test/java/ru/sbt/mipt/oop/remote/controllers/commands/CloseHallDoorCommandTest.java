package ru.sbt.mipt.oop.remote.controllers.commands;

import org.junit.Test;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CloseHallDoorCommandTest {

    @Test
    public void execute() {
        Door doorId1 = new Door(true, "1");
        Light lightId1 = new Light("1", true);
        Light lightId2 = new Light("2", false);
        Light lightId3 = new Light("3", true);
        Room kitchen = new Room(Arrays.asList(lightId1, lightId2),
                Arrays.asList(),
                "kitchen");
        Room hall = new Room(Arrays.asList(lightId3),
                Arrays.asList(doorId1),
                "hall");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, hall));

        Command closeHallCommand = new CloseHallDoorCommand(smartHome, "1");
        closeHallCommand.execute();

        //verify
        assertFalse(lightId1.isOn());
        assertFalse(lightId2.isOn());
        assertFalse(lightId3.isOn());
        assertFalse(doorId1.isOpen());
    }
}

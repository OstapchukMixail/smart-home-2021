package ru.sbt.mipt.oop.remote.controllers;

import org.junit.Test;
import ru.sbt.mipt.oop.remote.controllers.commands.*;
import ru.sbt.mipt.oop.remote.controllers.*;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class RemoteControlImplTest {
    private final Light lightId1 = new Light("1", false);
    private final Light lightId2 = new Light("2", true);
    private final Light lightId3 = new Light("3", false);
    private final Light lightId4 = new Light("4", true);
    private final Door doorId1 = new Door(false, "1");
    private final Door doorId2 = new Door(true, "2");
    private final Room kitchen = new Room(Arrays.asList(lightId1, lightId2),
            Arrays.asList(doorId1),
            "kitchen");
    private final Room hall = new Room(Arrays.asList(lightId3, lightId4),
            Arrays.asList(doorId2),
            "hall");
    private final SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, hall));
    private final Map<String, Command> buttonCodeToCommand = Map.of(
            "A", new ActivateAlarmCommand(smartHome.getAlarm(), "password"),
            "B", new CloseHallDoorCommand(smartHome, "2"),
            "C", new ToAlertCommand(smartHome.getAlarm()),
            "D", new TurnOffAllLightCommand(smartHome),
            "1", new TurnOnAllLightCommand(smartHome),
            "2", new TurnOnHallLightCommand(smartHome)
    );
    private final RemoteControl remoteControl = new RemoteControlImpl(buttonCodeToCommand);
    private final RemoteControlRegistry registry = new RemoteControlRegistry();


    public RemoteControlImplTest() {
        registry.registerRemoteControl(remoteControl, "id");
    }

    @Test
    public void activateAlarmWithRC(){
        remoteControl.onButtonPressed("A", "id");
        assertTrue(smartHome.getAlarm().isActivated());
    }

    @Test
    public void toAlertAlarmWithRC(){
        remoteControl.onButtonPressed("C", "id");
        assertTrue(smartHome.getAlarm().isAlert());
    }

    @Test
    public void closeHallWithRC(){
        remoteControl.onButtonPressed("B", "id");
        assertFalse(doorId2.isOpen());
    }

    @Test
    public void offAllLightWithRC(){
        remoteControl.onButtonPressed("D", "id");
        //verify
        assertFalse(lightId1.isOn());
        assertFalse(lightId2.isOn());
        assertFalse(lightId3.isOn());
        assertFalse(lightId4.isOn());
    }

    @Test
    public void onAllLightWithRC(){
        remoteControl.onButtonPressed("1", "id");
        //verify
        assertTrue(lightId1.isOn());
        assertTrue(lightId2.isOn());
        assertTrue(lightId3.isOn());
        assertTrue(lightId4.isOn());
    }

    @Test
    public void onHallLightWithRC(){
        remoteControl.onButtonPressed("2", "id");
        //verify
        assertFalse(lightId1.isOn());
        assertTrue(lightId2.isOn());
        assertTrue(lightId3.isOn());
        assertTrue(lightId4.isOn());
    }
}
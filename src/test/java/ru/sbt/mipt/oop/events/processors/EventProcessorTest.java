package ru.sbt.mipt.oop.events.processors;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Arrays;

import static org.junit.Assert.*;
public class EventProcessorTest {
    @Test
    public void closeOneDoorLeaveOthers() {
        //setup
        Door doorId1 = new Door(true, "1");
        Door doorId2 = new Door(true, "2");
        Door doorId3 = new Door(false, "3");
        Room room = new Room(Arrays.asList(),
                Arrays.asList(doorId1, doorId2, doorId3),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome);
        //execute
        doorEventProcessor.processEvent(new SensorEvent(SensorEventType.DOOR_CLOSED, "1"));
        //verify
        assertEquals(false, doorId1.isOpen());
        assertEquals(true, doorId2.isOpen());
        assertEquals(false, doorId3.isOpen());
    }

    @Test
    public void openOneDoorLeaveOthers() {
        //setup
        Door doorId1 = new Door(false, "1");
        Door doorId2 = new Door(false, "2");
        Door doorId3 = new Door(true, "3");
        Room room = new Room(Arrays.asList(),
                Arrays.asList(doorId1, doorId2),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome);
        //execute
        doorEventProcessor.processEvent(new SensorEvent(SensorEventType.DOOR_OPEN, "1"));
        //verify
        assertEquals(true, doorId1.isOpen());
        assertEquals(false, doorId2.isOpen());
        assertEquals(true, doorId3.isOpen());
    }

    @Test
    public void turnOffOneLightLeaveOthers() {
        //setup
        Light lightId1 = new Light("1", true);
        Light lightId2 = new Light("2", true);
        Light lightId3 = new Light("3", false);
        Room room = new Room(Arrays.asList(lightId1, lightId2, lightId3),
                Arrays.asList(),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome);
        //execute
        lightEventProcessor.processEvent(new SensorEvent(SensorEventType.LIGHT_OFF, "1"));
        //verify
        assertEquals(false, lightId1.isOn());
        assertEquals(true, lightId2.isOn());
        assertEquals(false, lightId3.isOn());
    }

    @Test
    public void turnOnOneLightLeaveOthers() {
        //setup
        Light lightId1 = new Light("1", false);
        Light lightId2 = new Light("2", true);
        Light lightId3 = new Light("3", false);
        Room room = new Room(Arrays.asList(lightId1, lightId2, lightId3),
                Arrays.asList(),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));
        LightEventProcessor lightEventProcessor = new LightEventProcessor(smartHome);
        //execute
        lightEventProcessor.processEvent(new SensorEvent(SensorEventType.LIGHT_ON, "1"));
        //verify
        assertEquals(true, lightId1.isOn());
        assertEquals(true, lightId2.isOn());
        assertEquals(false, lightId3.isOn());
    }

    @Test
    public void closeHallTurnOffAll() {
        //setup
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
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome);
        HallDoorEventProcessor hallDoorEventHandler = new HallDoorEventProcessor(smartHome);
        //execute
        SensorEvent closeHall = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
        doorEventProcessor.processEvent(closeHall);
        hallDoorEventHandler.processEvent(closeHall);
        //verify
        assertEquals(false, lightId1.isOn());
        assertEquals(false, lightId2.isOn());
        assertEquals(false, lightId3.isOn());
        assertEquals(false, doorId1.isOpen());
    }

    @Test
    public void closeNotHallLeaveLight() {
        //setup
        Door doorId1 = new Door(true, "1");
        Light lightId1 = new Light("1", true);
        Light lightId2 = new Light("2", false);
        Light lightId3 = new Light("3", true);
        Room kitchen = new Room(Arrays.asList(lightId1, lightId2),
                Arrays.asList(),
                "kitchen");
        Room bedroom = new Room(Arrays.asList(lightId3),
                Arrays.asList(doorId1),
                "bedroom");
        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, bedroom));
        DoorEventProcessor doorEventProcessor = new DoorEventProcessor(smartHome);
        HallDoorEventProcessor hallDoorEventHandler = new HallDoorEventProcessor(smartHome);
        //execute
        SensorEvent closeHall = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
        doorEventProcessor.processEvent(closeHall);
        hallDoorEventHandler.processEvent(closeHall);
        //verify
        assertEquals(true, lightId1.isOn());
        assertEquals(false, lightId2.isOn());
        assertEquals(true, lightId3.isOn());
        assertEquals(false, doorId1.isOpen());
    }
}
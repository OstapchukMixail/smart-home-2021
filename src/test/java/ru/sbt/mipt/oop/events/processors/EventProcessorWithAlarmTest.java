package ru.sbt.mipt.oop.events.processors;

import org.junit.Test;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.EventType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EventProcessorWithAlarmTest {

    @Test
    public void openDoorInActivated() {
        //setup
        Door doorId1 = new Door(false, "1");
        Room room = new Room(Arrays.asList(),
                Arrays.asList(doorId1),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));

        List<EventProcessor> eventProcessors = Arrays.asList(
                new LightEventProcessor(smartHome),
                new DoorEventProcessor(smartHome),
                new HallDoorEventProcessor(smartHome),
                new AlarmEventProcessor(smartHome.getAlarm())
        );

        EventProcessor eventProcessor = new EventProcessorDecorator(new AnyEventProcessor(eventProcessors),
                smartHome.getAlarm());
        //execute
        eventProcessor.processEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "password"));
        eventProcessor.processEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        //verify
        assertEquals(false, doorId1.isOpen());
        assertTrue(smartHome.getAlarm().isAlert());
    }

    @Test
    public void openDoorInDeactivated() {
        //setup
        Door doorId1 = new Door(false, "1");
        Door doorId2 = new Door(false, "2");
        Door doorId3 = new Door(true, "3");
        Room room = new Room(Arrays.asList(),
                Arrays.asList(doorId1, doorId2),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));

        List<EventProcessor> eventProcessors = Arrays.asList(
                new LightEventProcessor(smartHome),
                new DoorEventProcessor(smartHome),
                new HallDoorEventProcessor(smartHome),
                new AlarmEventProcessor(smartHome.getAlarm())

        );

        EventProcessor eventProcessor = new EventProcessorDecorator(new AnyEventProcessor(eventProcessors),
                smartHome.getAlarm());
        //execute
        eventProcessor.processEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        //verify
        assertEquals(true, doorId1.isOpen());
        assertFalse(smartHome.getAlarm().isAlert());
    }

    @Test
    public void openDoorInAlert() {
        //setup
        Door doorId1 = new Door(false, "1");
        Room room = new Room(Arrays.asList(),
                Arrays.asList(doorId1),
                "kitchen");
        SmartHome smartHome = new SmartHome(Arrays.asList(room));

        List<EventProcessor> eventProcessors = Arrays.asList(
                new LightEventProcessor(smartHome),
                new DoorEventProcessor(smartHome),
                new HallDoorEventProcessor(smartHome),
                new AlarmEventProcessor(smartHome.getAlarm())
        );

        EventProcessor eventProcessor = new EventProcessorDecorator(new AnyEventProcessor(eventProcessors),
                smartHome.getAlarm());
        //execute
        eventProcessor.processEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "password"));
        eventProcessor.processEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        eventProcessor.processEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        //verify
        assertEquals(false, doorId1.isOpen());
        assertTrue(smartHome.getAlarm().isAlert());
    }
}
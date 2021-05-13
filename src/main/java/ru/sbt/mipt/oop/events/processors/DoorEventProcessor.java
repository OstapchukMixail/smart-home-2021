package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Door;

public class DoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    public DoorEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(Event event) {
        if (!isEventValid(event.getType())) return;

        SensorEvent sensorEvent = (SensorEvent) event;

        smartHome.execute(component -> {
            if (component instanceof Door) {
                Door door = (Door) component;
                if (door.getId().equals(sensorEvent.getObjectId())) {
                    updateState(door, getState(event));
                }
            }
        });
    }

    private boolean isEventValid(EventType type) {
        return type == EventType.DOOR_OPEN || type == EventType.DOOR_CLOSED;
    }

    private boolean getState(Event event){
        final boolean isOpen = event.getType() == EventType.DOOR_OPEN;
        return isOpen;
    }

    private void updateState(Door door, boolean newState) {
        door.setOpen(newState);
        System.out.println("Door " + door.getId() + " was " + (newState ? "opened." : "closed."));
    }
}

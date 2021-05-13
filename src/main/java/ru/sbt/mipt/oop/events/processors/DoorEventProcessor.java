package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.events.helplibraries.LoopForProcessors;
import ru.sbt.mipt.oop.events.SensorEventType;

public class DoorEventProcessor implements EventProcessor {
    private final SmartHome smartHome;

    public DoorEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (!isEventValid(event.getType())) return;

        Door currentDoor = (new LoopForProcessors(smartHome)).searchDoor(event.getObjectId());
        if (currentDoor != null) {
            updateState(currentDoor, getState(event));
        }
    }

    private boolean isEventValid(SensorEventType type) {
        return type == SensorEventType.DOOR_OPEN || type == SensorEventType.DOOR_CLOSED;
    }

    private boolean getState(SensorEvent event){
        final boolean isOpen = event.getType() == SensorEventType.DOOR_OPEN;
        return isOpen;
    }

    private void updateState(Door door, boolean newState) {
        door.setOpen(newState);
        System.out.println("Door " + door.getId() + " was " + (newState ? "opened." : "closed."));
    }
}

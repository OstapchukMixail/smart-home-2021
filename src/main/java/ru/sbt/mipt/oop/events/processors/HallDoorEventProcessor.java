package ru.sbt.mipt.oop.events.processors;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandSenderImplementation;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.AllLightAction;
import ru.sbt.mipt.oop.action.CloseHallDoorAction;


public class HallDoorEventProcessor implements EventProcessor{
    private final SmartHome smartHome;

    public HallDoorEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }


    @Override
    public void processEvent(Event event) {
        if (!isEventValid(event)) return;

        SensorEvent sensorEvent = (SensorEvent) event;

        Action closeHallDoor = new CloseHallDoorAction(smartHome, sensorEvent.getObjectId());
        smartHome.execute(closeHallDoor);
    }

    private boolean isEventValid(Event event) {
        return (event.getType().equals(EventType.DOOR_CLOSED));
    }

}

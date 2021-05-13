package ru.sbt.mipt.oop.events.processors;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.commands.CommandSenderImplementation;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.SensorEventType.*;


public class HallDoorEventProcessor implements EventProcessor{
    private final SmartHome smartHome;

    public HallDoorEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }


    @Override
    public void processEvent(SensorEvent event) {
        if (!isEventValid(event)) return;

        smartHome.execute((homeComponent -> {
            if (homeComponent instanceof Room) {
                Room room = (Room) homeComponent;
                if (!room.getName().equals("hall")) {
                    return;
                }

                ifHallDoor(event.getObjectId());
            }
        }));
    }

    private boolean isEventValid(SensorEvent event) {
        return (event.getType().equals(SensorEventType.DOOR_CLOSED));
    }


    private void ifHallDoor(String id) {
        smartHome.execute((component -> {
            if (component instanceof Door) {
                Door door = (Door) component;
                if (door.getId().equals(id)) {
                    turnOffAllLight();
                    smartHome.execute((componentL -> {
                        if (componentL instanceof Light) {
                            Light light = (Light) componentL;
                            SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                            CommandSender sender = new CommandSenderImplementation();
                            sender.sendCommand(command);
                        }
                    }));
                }
            }
        }));
    }

    private void turnOffAllLight() {
        smartHome.execute((innComponent -> {
            if (innComponent instanceof Light) {
                Light light = (Light) innComponent;
                light.setOn(false);
                System.out.println(light.getId() + " was turned off.");
            }
        }));
    }
}

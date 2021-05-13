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
        
        for (Room room: smartHome.getRooms()) {
            for (Light light: room.getLights()) {
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                CommandSender sender = new CommandSenderImplementation();;
                sender.sendCommand(command);
            }
        }
        turnOffAllLight();
    }

    private boolean isEventValid(SensorEvent event) {
        return (event.getType().equals(SensorEventType.DOOR_CLOSED));
    }


    private boolean isHallDoor(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id) && room.getName().equals("hall")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void turnOffAllLight() {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
            }
        }
        System.out.println("All light is turned off.");
    }
}

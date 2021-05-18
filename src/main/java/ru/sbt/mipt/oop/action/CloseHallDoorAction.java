package ru.sbt.mipt.oop.action;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.commands.CommandSender;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.CommandSenderImplementation;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;

public class CloseHallDoorAction implements Action{
    private final SmartHome smartHome;
    private final String objectId;

    public CloseHallDoorAction(SmartHome smartHome, String objectId) {
        this.smartHome = smartHome;
        this.objectId = objectId;
    }

    private void turnOffAllLight() {
        Action offAllLight = new AllLightAction(false);
        smartHome.execute(offAllLight);
    }

    private void closeIfDoorIsHall(Room hall, String doorId) {
        hall.execute((component -> {
            if (component instanceof Door) {
                Door door = (Door) component;
                if (door.getId().equals(doorId)) {
                    closeDoor(door);
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

    private void closeDoor(Door door) {
        door.setOpen(false);
        System.out.println("Door " + door.getId() + " was closed.");
    }

    @Override
    public void run(Object component) {
        if (component instanceof Room) {
            Room room = (Room) component;
            if (!room.getName().equals("hall")) {
                return;
            }

            closeIfDoorIsHall(room, objectId);
        }
    }
}

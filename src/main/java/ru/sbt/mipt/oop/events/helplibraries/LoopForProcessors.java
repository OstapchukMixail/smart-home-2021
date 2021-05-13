package ru.sbt.mipt.oop.events.helplibraries;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;

public class LoopForProcessors {
    private final SmartHome smartHome;

    public LoopForProcessors(SmartHome smartHome) {
        this.smartHome = smartHome;
    }


    public Light searchLight(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(id)) {
                    return light;
                }
            }
        }
        return null;
    }

    public Door searchDoor(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    return door;
                }
            }
        }
        return null;
    }
}

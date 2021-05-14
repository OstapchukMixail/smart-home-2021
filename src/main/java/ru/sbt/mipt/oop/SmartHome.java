package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.Actionable;
import ru.sbt.mipt.oop.signaling.Alarm;

public class SmartHome implements Actionable {
    Collection<Room> rooms;
    private Alarm alarm = new Alarm("password");

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Alarm getAlarm() {
        return alarm;
    }

    @Override
    public void execute(Action action) {
        action.run(this);

        for(Room room : rooms)
            room.execute(action);
        alarm.execute(action);
    }
}

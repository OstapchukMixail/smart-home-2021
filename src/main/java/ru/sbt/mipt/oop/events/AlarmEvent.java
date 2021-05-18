package ru.sbt.mipt.oop.events;

public class AlarmEvent implements Event{
    private final EventType type;

    public String getCode() {
        return code;
    }

    private final String code;

    public AlarmEvent(EventType type, String code) {
        this.type = type;
        this.code = code;
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "type=" + type +
                ", code='" + code + '\'' +
                '}';
    }
}
package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.signaling.Alarm;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;

import static ru.sbt.mipt.oop.events.EventType.*;

public class AlarmEventProcessor implements  EventProcessor{
    private final Alarm alarm;

    public AlarmEventProcessor(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void processEvent(Event event) {
        if (!isEventValid(event)) return;

        AlarmEvent alarmEvent = (AlarmEvent) event;

        alarm.execute((component -> {
            if (component instanceof Alarm) {
                Alarm alarm = (Alarm) component;
                if (event.getType().equals(ALARM_ACTIVATE)) {
                    alarm.activate(alarmEvent.getCode());
                }

                if (event.getType().equals(ALARM_DEACTIVATE)) {
                    alarm.deactivate(alarmEvent.getCode());
                }
            }
        }));
    }

    private boolean isEventValid(Event event) {
        return (event.getType().equals(ALARM_ACTIVATE) || event.getType().equals(ALARM_DEACTIVATE));
    }
}
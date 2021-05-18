package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.signaling.Alarm;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.ActivateDeactivateAlarmAction;

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

        Action changeAlarmState = new ActivateDeactivateAlarmAction(isToActivate(event), alarmEvent.getCode());
        alarm.execute(changeAlarmState);
    }

    private boolean isEventValid(Event event) {
        return (event.getType().equals(ALARM_ACTIVATE) || event.getType().equals(ALARM_DEACTIVATE));
    }

    private boolean isToActivate(Event event) {
        return event.getType().equals(ALARM_ACTIVATE);
    }
}
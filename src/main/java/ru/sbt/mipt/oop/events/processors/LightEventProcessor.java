package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.OneLightAction;

public class LightEventProcessor implements EventProcessor{
    private final SmartHome smartHome;

    public LightEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(Event event) {
        if (!isEventValid(event.getType())) return;

        SensorEvent sensorEvent = (SensorEvent) event;

        Action setLight = new OneLightAction(sensorEvent.getObjectId(), getState(event));
        smartHome.execute(setLight);
    }

    private boolean isEventValid(EventType type) {
        return type == EventType.LIGHT_OFF || type == EventType.LIGHT_ON;
    }

    private boolean getState(Event event){
        final boolean isOn = event.getType() == EventType.LIGHT_ON;
        return isOn;
    }

}

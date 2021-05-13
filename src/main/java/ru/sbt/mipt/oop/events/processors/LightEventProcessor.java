package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;

public class LightEventProcessor implements EventProcessor{
    private final SmartHome smartHome;

    public LightEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(Event event) {
        if (!isEventValid(event.getType())) return;

        SensorEvent sensorEvent = (SensorEvent) event;
        smartHome.execute(component -> {
            if (component instanceof Light) {
                Light light = (Light) component;
                if (light.getId().equals(sensorEvent.getObjectId())) {
                    updateState(light, getState(event));
                }
            }
        });
    }

    private boolean isEventValid(EventType type) {
        return type == EventType.LIGHT_OFF || type == EventType.LIGHT_ON;
    }

    private boolean getState(Event event){
        final boolean isOn = event.getType() == EventType.LIGHT_ON;
        return isOn;
    }

    private void updateState(Light light, boolean newState) {
        light.setOn(newState);
        System.out.println("Light " + light.getId() + " was turned " + (newState ? "on." : "off."));
    }
}

package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.events.helplibraries.LoopForProcessors;
import ru.sbt.mipt.oop.events.SensorEventType;

public class LightEventProcessor implements EventProcessor{
    private final SmartHome smartHome;

    public LightEventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void processEvent(SensorEvent event) {
        if (!isEventValid(event.getType())) return;

        Light currentLight = (new LoopForProcessors(smartHome)).searchLight(event.getObjectId());
        if (currentLight != null) {
            updateState(currentLight, getState(event));
        }
    }

    private boolean isEventValid(SensorEventType type) {
        return type == SensorEventType.LIGHT_OFF || type == SensorEventType.LIGHT_ON;
    }

    private boolean getState(SensorEvent event){
        final boolean isOn = event.getType() == SensorEventType.LIGHT_ON;
        return isOn;
    }

    private void updateState(Light light, boolean newState) {
        light.setOn(newState);
        System.out.println("Light " + light.getId() + " was turned " + (newState ? "on." : "off."));
    }
}

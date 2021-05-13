package ru.sbt.mipt.oop.action;

import ru.sbt.mipt.oop.Light;

public class OneLightAction implements Action{
    private final String objectId;
    private final boolean isToOn;

    public OneLightAction(String objectId, boolean isToOn) {
        this.objectId = objectId;
        this.isToOn = isToOn;
    }

    @Override
    public void run(Object component) {
        if (component instanceof Light) {
            Light light = (Light) component;
            if (light.getId().equals(objectId)) {
                updateState(light, isToOn);
            }
        }
    }

    private void updateState(Light light, boolean newState) {
        light.setOn(newState);
        System.out.println("Light " + light.getId() + " was turned " + (newState ? "on." : "off."));
    }
}
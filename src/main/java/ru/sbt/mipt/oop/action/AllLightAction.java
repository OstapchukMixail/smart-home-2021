package ru.sbt.mipt.oop.action;

import ru.sbt.mipt.oop.Light;

public class AllLightAction implements Action{
    private final boolean isToOn;

    public AllLightAction(boolean isToOn) {
        this.isToOn = isToOn;
    }

    @Override
    public void run(Object component) {
        if (component instanceof Light) {
            Light light = (Light) component;
            light.setOn(isToOn);
            System.out.println("Light " + light.getId() + " was turned off.");
        }
    }
}

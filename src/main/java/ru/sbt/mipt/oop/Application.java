package ru.sbt.mipt.oop;

import java.util.Arrays;
import java.util.List;
import ru.sbt.mipt.oop.homereader.SmartHomeReaderImplementation;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.processors.*;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class Application {

    public static void main(String... args) {
        SmartHome smartHome = (new SmartHomeReaderImplementation("smart-home-1.js")).read();
        List<EventProcessor> eventProcessors = Arrays.asList(
                new LightEventProcessor(smartHome),
                new DoorEventProcessor(smartHome)
        );
        EventCreation eventLoop = new EventCreation(
                new RandomEventProcessor(eventProcessors),
                new GettingNextSensorEventImplementation()
        );
        eventLoop.createEvent();
    }
}

package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.adapter.EventHandlerAdapter;
import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.processors.DoorEventProcessor;
import ru.sbt.mipt.oop.events.processors.EventProcessor;
import ru.sbt.mipt.oop.events.processors.HallDoorEventProcessor;
import ru.sbt.mipt.oop.events.processors.LightEventProcessor;
import ru.sbt.mipt.oop.homereader.SmartHomeReaderImplementation;

import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean
    SmartHome smartHome() {
        return (new SmartHomeReaderImplementation("smart-home-1.js")).read();
    }

    @Bean
    EventProcessor lightEventProcessor() {
        return new LightEventProcessor(smartHome());
    }

    @Bean
    EventProcessor getDoorEventProcessor() {
        return new DoorEventProcessor(smartHome());
    }

    @Bean
    EventProcessor getHallDoorEventProcessor() {
        return new HallDoorEventProcessor(smartHome());
    }

    @Bean
    Map<String, EventType> dict(){
        return Map.of(
                "LightIsOn", EventType.LIGHT_ON,
                "LightIsOff", EventType.LIGHT_OFF,
                "DoorIsOpen", EventType.DOOR_OPEN,
                "DoorIsClosed", EventType.DOOR_CLOSED
        );
    }


    @Bean
    SensorEventsManager sensorEventsManager(List<EventProcessor> eventHandlers) {
        SensorEventsManager manager = new SensorEventsManager();
        eventHandlers.forEach(handler -> manager.registerEventHandler(new EventHandlerAdapter(handler, dict())));
        return manager;
    }
}
package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.adapter.EventHandlerAdapter;
import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.events.processors.*;
import ru.sbt.mipt.oop.signaling.Alarm;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.remote.controllers.RemoteControlRegistry;
import ru.sbt.mipt.oop.remote.controllers.commands.*;
import ru.sbt.mipt.oop.homereader.JsonSmartHomeReader;
import ru.sbt.mipt.oop.remote.controllers.RemoteControl;
import ru.sbt.mipt.oop.remote.controllers.RemoteControlImpl;

import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean
    SmartHome smartHome() {
        return (new JsonSmartHomeReader("smart-home-1.js")).read();
    }

    @Bean
    EventProcessor lightEventProcessor() {
        return new EventProcessorDecorator(new LightEventProcessor(smartHome()), smartHome().getAlarm());
    }

    @Bean
    EventProcessor getDoorEventProcessor() {
        return new EventProcessorDecorator(new DoorEventProcessor(smartHome()), smartHome().getAlarm());
    }

    @Bean
    EventProcessor getHallDoorEventProcessor() {
        return new EventProcessorDecorator(new HallDoorEventProcessor(smartHome()), smartHome().getAlarm());
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

    @Bean
    Alarm alarm(){
        return smartHome().getAlarm();
    }

    @Bean
    Command activateAlarmCommand() {
        return new ActivateAlarmCommand(alarm(), "password");
    }

    @Bean
    Command closeHallDoorCommand() {
        return new CloseHallDoorCommand(smartHome(), "4");
    }

    @Bean
    Command toAlertCommand() {
        return new ToAlertCommand(alarm());
    }

    @Bean
    Command turnOffAllLightCommand() {
        return new TurnOffAllLightCommand(smartHome());
    }

    @Bean
    Command turnOnAllLightCommand() {
        return new TurnOnAllLightCommand(smartHome());
    }

    @Bean
    Command turnOnHallLightCommand() {
        return new TurnOnHallLightCommand(smartHome());
    }


    Map<String, Command> remoteControlDict(){
        return Map.of(
                "A", activateAlarmCommand(),
                "B", closeHallDoorCommand(),
                "C", toAlertCommand(),
                "D", turnOffAllLightCommand(),
                "1", turnOnHallLightCommand(),
                "2", turnOnAllLightCommand()
        );
    }

    @Bean
    RemoteControl remoteControl() {
        return new RemoteControlImpl(remoteControlDict());
    }

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry registry = new RemoteControlRegistry();
        registry.registerRemoteControl(remoteControl(), "id");
        return registry;
    }

}
package ru.sbt.mipt.oop.signaling;

import org.junit.Test;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.processors.AlarmEventProcessor;

import static org.junit.Assert.assertTrue;

public class AlarmTest {
    private final String code = "promike";
    private final Alarm alarm = new Alarm(code);
    private final AlarmEventProcessor alarmEventProcessor = new AlarmEventProcessor(alarm);


    private void activateAlarm(String code) {
        AlarmEvent event = new AlarmEvent(EventType.ALARM_ACTIVATE, code);
        alarmEventProcessor.processEvent(event);
    }


    private void deactivateAlarm(String code) {
        AlarmEvent event = new AlarmEvent(EventType.ALARM_DEACTIVATE, code);
        alarmEventProcessor.processEvent(event);
    }

    @Test
    public void alarmActivateWithCorrectCode() {
        activateAlarm("promike");
        assertTrue(alarm.isActivated());
    }

    @Test
    public void alarmActivateWithWrongCode() {
        activateAlarm("prrromikee");
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void alarmDeactivateWithCorrectCode() {
        activateAlarm("promike");
        deactivateAlarm("promike");
        assertTrue(alarm.isDeactivated());
    }

    @Test
    public void alarmDeactivateWithWrongCode() {
        activateAlarm("promike");
        deactivateAlarm("promikepromike");
        assertTrue(alarm.isAlert());
    }

}
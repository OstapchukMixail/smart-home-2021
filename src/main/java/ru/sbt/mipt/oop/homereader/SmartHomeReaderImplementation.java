package ru.sbt.mipt.oop.homereader;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.SmartHome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeReaderImplementation implements SmartHomeReader {
    private final String filename;

    public SmartHomeReaderImplementation(String filename) {
        this.filename = filename;
    }

    @Override
    public SmartHome read() {
        try {
            // считываем состояние дома из файла
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            SmartHome smartHome = gson.fromJson(json, SmartHome.class);
            return smartHome;
        } catch (IOException e) {
            e.printStackTrace();
            return new SmartHome();
        }
    }
}

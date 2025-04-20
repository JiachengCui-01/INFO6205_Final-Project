package mcts.util;

import java.io.FileWriter;
import java.io.IOException;

public class RunLogger {
    private final String fileName;

    public RunLogger(String fileName) {
        this.fileName = fileName;
    }

    public void log(int iterations, long timeMillis, String result) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(iterations + "," + timeMillis + "," + result + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + fileName);
        }
    }
}

package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Tab1 implements Initializable {

    @FXML
    TextArea textArea;

    // https://www.baeldung.com/run-shell-command-in-java
    private static class StreamGobbler implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .map((string) -> string + System.lineSeparator())
                    .forEach(consumer);
        }
    }

    public void initialize(URL url, ResourceBundle resources) {
        try {
            String command = "sh -c ls";
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                command = "cmd.exe /c dir";
            }
            Process process = Runtime.getRuntime().exec(command);
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), textArea::appendText);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(streamGobbler);
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

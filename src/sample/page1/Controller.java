package sample.page1;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TableView<DataModel> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<DataModel> records = table.getItems();

        Properties config = getConfig("config.properties");

        Enumeration<Object> keys = config.keys();
        while (keys.hasMoreElements()) {
            String url = config.getProperty((String) keys.nextElement());
            String data = "";
            records.add(new DataModel(url, data));
        }

    }

    /**
     * @param resourceFileName resource identifier containing configuration
     * @return configuration
     */
    private Properties getConfig(String resourceFileName) {

        // @see https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java
        Properties config = new Properties();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

}

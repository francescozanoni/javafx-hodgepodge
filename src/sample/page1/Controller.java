package sample.page1;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sample.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {

    @FXML
    TableView<DataModel> table;

    @FXML
    Button button1;

    @FXML
    Button button2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // URLs are loaded to table, via data model binding.
        ObservableList<DataModel> records = table.getItems();
        Properties config = Utils.getConfig("config.properties");
        Enumeration<Object> keys = config.keys();
        while (keys.hasMoreElements()) {
            String url = config.getProperty((String) keys.nextElement());
            String data = "";
            records.add(new DataModel(url, data));
        }

    }

    /**
     * Download all URLs and display content length, with many ExecutorService's using execute().
     *
     * THIS SOLUTION DOES NOT FREEZE THE UI: IT IS ASYNCHRONOUS.
     *
     * @see <a href="https://deitel.com/java-9-for-programmers/"></a>
     */
    public void handleButton1Action() {

        // button1 cannot be disabled by button1.setDisable(true) and re-enabled by button1.setDisable(false):
        // the asynchronous execution makes both statements executed immediately,
        // independently from tasks run by ExecutorService.

        for (DataModel record : table.getItems()) {
            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    updateMessage("...");
                    int result = Utils.getUrlContent(record.getUrl()).length();
                    updateMessage(String.valueOf(result));
                    // javafx.concurrent.Task must return a value, even if unused.
                    return result;
                }
            };
            record.dataProperty().bind(task.messageProperty());
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(task);
            executor.shutdown();
        }

    }

    /**
     * Download all URLs and display content length, with a single ExecutorService using invokeAll().
     *
     * THIS SOLUTION MUST NOT BE USED: IT IS FASTER, BUT IT FREEZES THE UI AND DOES NOT DISPLAY "...".
     *
     * @throws InterruptedException if anything goes wrong with ExecutorService.invokeAll()
     *
     * @see <a href="https://stackoverflow.com/questions/46645489/java-invoke-collection-of-tasks-as-with-invokeall"></a>
     */
    public void handleButton2Action() throws InterruptedException {

        ArrayList<Callable<Integer>> taskList = new ArrayList<>();

        for (DataModel record : table.getItems()) {
            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    updateMessage("...");
                    int result = Utils.getUrlContent(record.getUrl()).length();
                    updateMessage(String.valueOf(result));
                    // javafx.concurrent.Task must return a value, even if unused.
                    return result;
                }
            };
            record.dataProperty().bind(task.messageProperty());
            // javafx.concurrent.Task cannot be used with ExecutorService.invokeAll(),
            // it must therefore be wrapped into a Callable.
            Callable<Integer> callable = () -> {
                task.run();
                return task.getValue();
            };
            taskList.add(callable);
        }

        ExecutorService executor = Executors.newFixedThreadPool(taskList.size());
        executor.invokeAll(taskList);
        executor.shutdown();

    }

}

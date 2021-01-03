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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {

    @FXML
    TableView<TableRecordBean> table;

    @FXML
    Button button1;

    @FXML
    Button button2;

    @FXML
    Button button3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // URLs are loaded to table, via data model binding.

        Properties config = Utils.getConfig("config.properties");
        ObservableList<TableRecordBean> recordItemCollection = table.getItems();

        for (Object url : config.values()) {
            TableRecordBean recordBean = new TableRecordBean();
            recordBean.setUrl(String.valueOf(url));
            recordBean.setData("");
            recordItemCollection.add(recordBean);
        }

    }

    /**
     * Download all URLs and display content length, with many ExecutorService's using execute().
     *
     * With data binding.
     *
     * THIS SOLUTION DOES NOT FREEZE THE UI: IT IS ASYNCHRONOUS.
     *
     * @see <a href="https://deitel.com/java-9-for-programmers/"></a>
     */
    public void handleButton1Action() {

        // button1 cannot be disabled by button1.setDisable(true) and re-enabled by button1.setDisable(false):
        // the asynchronous execution makes both statements executed immediately,
        // independently from tasks run by ExecutorService.

        for (TableRecordBean recordBean : table.getItems()) {
            Task<Integer> task = new Task<>() {
                @Override
                protected Integer call() {
                    updateMessage("...");
                    int result = Utils.getUrlContent(recordBean.getUrl()).length();
                    updateMessage(String.valueOf(result));
                    // javafx.concurrent.Task must return a value, even if unused.
                    return result;
                }
            };
            recordBean.dataProperty().bind(task.messageProperty());
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(task);
            executor.shutdown();
        }

    }

    /**
     * Download all URLs and display content length, with many ExecutorService's using execute().
     *
     * Without data binding.
     *
     * THIS SOLUTION DOES NOT FREEZE THE UI: IT IS ASYNCHRONOUS.
     *
     * @see <a href="https://deitel.com/java-9-for-programmers/"></a>
     */
    public void handleButton2Action() {

        // button2 cannot be disabled by button2.setDisable(true) and re-enabled by button2.setDisable(false):
        // the asynchronous execution makes both statements executed immediately,
        // independently from tasks run by ExecutorService.

        for (TableRecordBean recordBean : table.getItems()) {

            // This is required, in order to avoid "java.lang.RuntimeException: A bound value cannot be set.",
            // occurring after handleButton1Action() execution.
            recordBean.dataProperty().unbind();

            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(() -> {
                recordBean.setData("...");
                int result = Utils.getUrlContent(recordBean.getUrl()).length();
                recordBean.setData(String.valueOf(result));
            });
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
    public void handleButton3Action() throws InterruptedException {

        ArrayList<Callable<Integer>> taskList = new ArrayList<>();

        for (TableRecordBean record : table.getItems()) {
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

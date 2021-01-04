package sample.controllers;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sample.IbanExtractor;
import sample.Utils;
import sample.beans.IbanBean;
import sample.beans.TableRecordBean;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Within methods running ExecutorService.execute(), buttons cannot be
// disabled by buttonN.setDisable(true) and re-enabled by buttonN.setDisable(false):
// the asynchronous execution makes both statements executed immediately,
// independently from code run by ExecutorService.

public class Page1 implements Initializable {

    @FXML
    TableView<TableRecordBean> url_table;

    @FXML
    TableView<IbanBean> iban_table;

    @FXML
    Button button1, button2, button3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // If only "visible" property is set, the element is hidden but it still uses room.
        // In order to completely hide an element, "managed" property must be bound to "visible" property.
        // https://stackoverflow.com/questions/28558165/javafx-setvisible-hides-the-element-but-doesnt-rearrange-adjacent-nodes
        iban_table.managedProperty().bind(iban_table.visibleProperty());

        // URLs are loaded to table, via data model binding.

        Properties config = Utils.getConfig("config.properties");
        ObservableList<TableRecordBean> recordItemCollection = url_table.getItems();

        for (Object url : config.values()) {
            TableRecordBean recordBean = new TableRecordBean();
            recordBean.setUrl(String.valueOf(url));
            recordBean.setData("");
            recordItemCollection.add(recordBean);
        }

    }

    /**
     * Download all URLs and display content length, with many ExecutorService's using execute().
     * <p>
     * With data binding between record bean and asynchronous code.
     * <p>
     * THIS SOLUTION DOES NOT FREEZE THE UI: IT IS ASYNCHRONOUS.
     *
     * @see <a href="https://deitel.com/java-9-for-programmers/"></a>
     */
    public void handleButton1Action() {

        for (TableRecordBean recordBean : url_table.getItems()) {
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
     * <p>
     * Without data binding between record bean and asynchronous code.
     * <p>
     * THIS SOLUTION DOES NOT FREEZE THE UI: IT IS ASYNCHRONOUS.
     */
    public void handleButton2Action() {

        iban_table.setVisible(true);

        for (TableRecordBean recordBean : url_table.getItems()) {

            // This is required, in order to avoid "java.lang.RuntimeException: A bound value cannot be set.",
            // occurring after handleButton1Action() execution.
            if (recordBean.dataProperty().isBound()) {
                recordBean.dataProperty().unbind();
            }

            recordBean.setData("...");

            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(() -> {
                String result = Utils.getUrlContent(recordBean.getUrl());
                recordBean.setData(String.valueOf(result.length()));
                // Single IBAN codes are added to iban_table.
                // TODO refactor to separate method
                String[] ibans = IbanExtractor.run(result);
                for (String iban : ibans) {
                    IbanBean ibanBean = new IbanBean();
                    ibanBean.setCode(iban);
                    // TODO remove duplicate IBAN codes
                    iban_table.getItems().add(ibanBean);
                }
            });
            executor.shutdown();
        }

    }

    /**
     * Download all URLs and display content length, with a single ExecutorService using invokeAll().
     * <p>
     * THIS SOLUTION MUST NOT BE USED: IT IS FASTER, BUT IT FREEZES THE UI AND DOES NOT DISPLAY "...".
     *
     * @throws InterruptedException if anything goes wrong with ExecutorService.invokeAll()
     * @see <a href="https://stackoverflow.com/questions/46645489/java-invoke-collection-of-tasks-as-with-invokeall"></a>
     */
    public void handleButton3Action() throws InterruptedException {

        ArrayList<Callable<Integer>> taskList = new ArrayList<>();

        for (TableRecordBean record : url_table.getItems()) {
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

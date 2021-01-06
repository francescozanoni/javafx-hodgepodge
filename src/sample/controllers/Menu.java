package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    MenuBar mainMenuBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainMenuBar.getMenus().get(0).getItems().get(2).setDisable(true);
    }

    @FXML
    protected void handleMenuClicked(ActionEvent event) throws IOException {

        MenuItem clickedMenu = (MenuItem) event.getSource();

        if (clickedMenu.getText().equals("Exit")) {
            // https://stackoverflow.com/questions/52456624/javafx-quit-menuitem
            Platform.exit();
        }

        if (clickedMenu.getText().equals("Page 1")) {
            changeScene(getClass().getResource("../../page1.fxml"));
        }

        if (clickedMenu.getText().equals("Page 2")) {
            changeScene(getClass().getResource("../../page2.fxml"));
        }

    }

    private void changeScene(URL fxmlUrl) throws IOException {
        // https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during-initialization
        Stage stage = (Stage) mainMenuBar.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(clazz -> {
            // https://www.logicbig.com/how-to/code-snippets/jcode-reflection-class-getconstructor.html
            try {
                Constructor<?> c = clazz.getConstructor();
                return c.newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // TODO remove this null
                return null;
            }
        });
        // https://stackoverflow.com/questions/28387218/why-does-my-javafx-stage-not-want-to-load
        // http://java-no-makanaikata.blogspot.com/2012/11/javafx-fxml-root-value-already-specified.html
        // https://stackoverflow.com/questions/8275499/how-to-call-getclass-from-a-static-method-in-java
        loader.setLocation(fxmlUrl);
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
    }

}

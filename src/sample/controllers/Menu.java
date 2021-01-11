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
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    MenuBar mainMenuBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainMenuBar.getMenus().get(0).getItems().get(3).setDisable(true);
    }

    @FXML
    protected void handleMenuClicked(ActionEvent event) throws IOException {

        MenuItem clickedMenu = (MenuItem) event.getSource();

        if (clickedMenu.getText().equals("Exit")) {
            // https://stackoverflow.com/questions/52456624/javafx-quit-menuitem
            Platform.exit();
        }

        if (clickedMenu.getText().equals("Page 1")) {
            changePage(getClass().getResource("../../page1.fxml"));
        }

        if (clickedMenu.getText().equals("Page 2")) {
            changePage(getClass().getResource("../../page2.fxml"));
        }

        if (clickedMenu.getText().equals("Page 3")) {
            changePage(getClass().getResource("../../page3.fxml"));
        }

    }

    private void changePage(URL fxmlUrl) throws IOException {
        // https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during-initialization
        Stage stage = (Stage) mainMenuBar.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();

        // https://stackoverflow.com/questions/28387218/why-does-my-javafx-stage-not-want-to-load
        // http://java-no-makanaikata.blogspot.com/2012/11/javafx-fxml-root-value-already-specified.html
        loader.setLocation(fxmlUrl);
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
    }

}

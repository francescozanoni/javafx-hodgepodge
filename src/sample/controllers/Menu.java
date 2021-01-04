package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    MenuBar mainMenuBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainMenuBar.getMenus().get(0).getItems().get(1).setDisable(true);
    }

    @FXML
    protected void handleMenuClicked(ActionEvent event) {

        MenuItem clickedMenu = (MenuItem) event.getSource();

        if (clickedMenu.getText().equals("Exit")) {
            // https://stackoverflow.com/questions/52456624/javafx-quit-menuitem
            Platform.exit();
        }

    }

}

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
        // Menu item with text "Disable programmatically" is disabled.
        mainMenuBar
                .getMenus()
                .get(0)
                .getItems()
                // Menu.getItems() returns non MenuItems, as well,
                // therefore text availability must be checked.
                .filtered(item -> item.getText() != null && item.getText().equals("Disabled programmatically"))
                .get(0)
                .setDisable(true);
    }

    @FXML
    protected void handleMenuClicked(ActionEvent event) throws IOException {

        MenuItem clickedMenu = (MenuItem) event.getSource();

        if (clickedMenu.getText().equals("Exit")) {
            // https://stackoverflow.com/questions/52456624/javafx-quit-menuitem
            Platform.exit();
        }

        switch (clickedMenu.getText()) {
            case "Page 1":
                changePage(getClass().getResource("../../page1.fxml"));
                break;
            case "Page 2":
                changePage(getClass().getResource("../../page2.fxml"));
                break;
            case "Page 3":
                changePage(getClass().getResource("../../page3.fxml"));
                break;
            case "Page 4":
                changePage(getClass().getResource("../../page4.fxml"));
                break;
            default:
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

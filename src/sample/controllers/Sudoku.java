package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import sample.SudokuValueGenerator;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Sudoku implements Initializable {

    @FXML
    private GridPane mainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Integer[][] values = new SudokuValueGenerator().generate();

        LinkedList<String> queue = new LinkedList<>();

        // Populate value queue, in order to provide value to the grid with the correct order.
        for (Integer[] value : values) {
            for (Integer integer : value) {
                queue.addLast(String.valueOf(integer));
            }
        }

        // Populate the grid with queue values.
        mainGrid.getChildren()
                .forEach(sectionGrid -> ((GridPane) sectionGrid)
                        .getChildren()
                        .forEach(button -> ((Button) button).setText(queue.removeFirst())));

    }

}

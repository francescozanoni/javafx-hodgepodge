package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Sudoku implements Initializable {

    @FXML
    private GridPane mainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Integer[][] values = new SudokuValueGenerator().generate();
        // String valuesAsString = renderIntegerMatrixAsString(values);

        // Populate grid with static values.
        mainGrid.getChildren()
                .forEach(sectionGrid -> ((GridPane) sectionGrid)
                        .getChildren()
                        .forEach(button -> ((Button) button).setText("A")));

    }

    private String renderIntegerMatrixAsString(Integer[][] matrix) {

        StringBuilder incrementalString = new StringBuilder();

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++) {
                incrementalString.append(matrix[rowIndex][columnIndex]).append(" ");
                if (columnIndex == matrix[rowIndex].length - 1 && rowIndex < matrix.length - 1) {
                    incrementalString.append("\n");
                }
            }
        }

        return incrementalString.toString();
    }

}

package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import sample.SudokuValueGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class Sudoku implements Initializable {

    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Integer[][] values = new SudokuValueGenerator().generate();
        String valuesAsString = renderIntegerMatrixAsString(values);

        textArea.setText(valuesAsString);

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

package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Sudoku implements Initializable {

    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Integer[][] matrix = getIntegerMatrix();

        String matrixAsString = getIntegerMatrixAsString(matrix);

        textArea.setText(matrixAsString);

    }

    private Integer[][] getIntegerMatrix() {

        List<Integer> list = new java.util.ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

        Integer[][] matrix = new Integer[9][9];

        for (int i = 0; i < matrix.length; i++) {
            // Randomize list
            // https://stackoverflow.com/questions/5505927/how-to-generate-a-random-permutation-in-java
            java.util.Collections.shuffle(list);
            // List to array
            // https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
            matrix[i] = list.toArray(new Integer[0]);
        }

        return matrix;
    }

    private String getIntegerMatrixAsString(Integer[][] matrix) {

        StringBuilder incrementalString = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                incrementalString.append(matrix[i][j]).append(" ");
                if (j == matrix[i].length - 1 && i < matrix.length - 1) {
                    incrementalString.append("\n");
                }
            }
        }

        return incrementalString.toString();
    }

}

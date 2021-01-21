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

        String matrixAsString = new SudokuValueGenerator().generateAsString();

        textArea.setText(matrixAsString);

    }

}

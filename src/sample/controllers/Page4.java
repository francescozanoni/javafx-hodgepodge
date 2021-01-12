package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import sample.beans.SudokuRowBean;

import java.net.URL;
import java.util.ResourceBundle;

public class Page4 implements Initializable {

    @FXML
    private TableView<SudokuRowBean> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<SudokuRowBean> sudokuRowBeans = table.getItems();

        for (int i = 0; i < 9; i++) {
            SudokuRowBean rowBean = new SudokuRowBean();
            rowBean.setCol0("0");
            rowBean.setCol1("1");
            rowBean.setCol2("2");
            rowBean.setCol3("3");
            rowBean.setCol4("4");
            rowBean.setCol5("5");
            rowBean.setCol6("6");
            rowBean.setCol7("7");
            rowBean.setCol8("8");
            sudokuRowBeans.add(rowBean);
        }

    }

}

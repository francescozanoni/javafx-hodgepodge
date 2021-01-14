// Extracted from https://github.com/lankydan/JavaFX-Table-Tutorial

package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import sample.Person;
import sample.beans.PersonBean;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditableTable implements Initializable {

    @FXML
    private TableView<PersonBean> table;

    private final ObservableList<PersonBean> observableData = FXCollections.observableArrayList();

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

    @FXML
    private TableColumn<PersonBean, String> firstNameColumn, lastNameColumn;

    @FXML
    private TableColumn<PersonBean, Date> dateOfBirthColumn;

    @FXML
    private TableColumn<PersonBean, Double> salaryColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        table.setEditable(true);
        table.setItems(observableData);

        try {
            List<Person> data = Arrays.asList(
                    new Person("Dan", "Newton", dateFormatter.parse("06/01/1994"), 22000),
                    new Person("George", "Newton", dateFormatter.parse("24/01/1995"), 15021),
                    new Person("Laura", "So", dateFormatter.parse("24/04/1995"), 0),
                    new Person("Jamie", "Harwood", dateFormatter.parse("15/12/9999"), 30000),
                    new Person("Michael", "Collins", dateFormatter.parse("01/01/0001"), 299),
                    new Person("Stuart", "Kerrigan", dateFormatter.parse("06/10/1894"), 100000)
            );
            data.forEach(person -> {
                PersonBean personBean = new PersonBean();
                personBean.setFirstName(person.firstName);
                personBean.setLastName(person.lastName);
                personBean.setDateOfBirth(person.dateOfBirth);
                personBean.setSalary(person.salary);
                observableData.add(personBean);
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setupFirstNameColumn();
        setupSurnameColumn();
        setupDateOfBirthColumn();
        setupSalaryColumn();

    }

    private void setupFirstNameColumn() {
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        // updates the firstName field on the PersonTableData object to the committed value
        firstNameColumn.setOnEditCommit(event -> {
            String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFirstName(value);
        });
    }

    private void setupSurnameColumn() {
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        // updates the lastName field on the PersonTableData object to the committed value
        lastNameColumn.setOnEditCommit(event -> {
            String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLastName(value);
        });
    }

    private void setupDateOfBirthColumn() {
        // formats the display value to display dates in the form of dd/MM/yyyy
        dateOfBirthColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter(DATE_PATTERN)));
        // updates the dateOfBirth field on the PersonTableData object to the committed value
        dateOfBirthColumn.setOnEditCommit(event -> {
            Date value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDateOfBirth(value);
        });
    }

    private void setupSalaryColumn() {
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        // updates the salary field on the PersonTableData object to the committed value
        salaryColumn.setOnEditCommit(event -> {
            Double value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSalary(value);
        });
    }

}

// Extracted from https://github.com/lankydan/JavaFX-Table-Tutorial

package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class PersonTableData {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleObjectProperty<Date> dateOfBirth;
    private final SimpleDoubleProperty salary;

    public PersonTableData(Person person) {
        this.firstName = new SimpleStringProperty(person.firstName);
        this.lastName = new SimpleStringProperty(person.lastName);
        this.dateOfBirth = new SimpleObjectProperty<>(person.dateOfBirth);
        this.salary = new SimpleDoubleProperty(person.salary);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public Date getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public double getSalary() {
        return salary.get();
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

}

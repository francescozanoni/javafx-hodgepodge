// Extracted from https://github.com/lankydan/JavaFX-Table-Tutorial

package sample;

import java.util.Date;

public class Person {

    public String firstName;
    public String lastName;
    public Date dateOfBirth;
    public double salary;

    public Person(String firstName, String lastName, Date dateOfBirth, double salary) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }

}

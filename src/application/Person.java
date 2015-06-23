package application;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 12 , 2015 15:11
 */

public class Person {


  private int id;

  private String firstName;

  private String middleName;

  public Person(int id, String firstName, String middleName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  private String lastName;

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String toString() {
    return String.format("Person [id=%s , first_name=%s,middle_name=%s ,  lastName=%s]",
            id, firstName, middleName, lastName);
  }
}

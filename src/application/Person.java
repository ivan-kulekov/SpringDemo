package application;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 12 , 2015 15:11
 */

public class Person {


  public final int id;

  public final String firstName;

  public final String middleName;

  public final String lastName;

  public Person(int id, String firstName, String middleName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format("Person [id=%s , first_name=%s,middle_name=%s ,  lastName=%s]",
            id, firstName, middleName, lastName);
  }
}

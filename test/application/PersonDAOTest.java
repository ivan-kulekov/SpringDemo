package application;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 23 , 2015 14:34
 */
public class PersonDAOTest {

  public PersonPersistence personDAO;
  public PersonRepository personRepository;

  @Before
  public void setUp() {
    personDAO = new PersonPersistence();
  }


  @Test
  public void addPersonToDatabase() throws IOException, SQLException {

    Person person = new Person(1, "Ivan", "Ivanov", "Ivanov");

    personRepository.addPerson(person);


  }
}

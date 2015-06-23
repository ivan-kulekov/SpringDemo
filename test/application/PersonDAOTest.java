package application;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 23 , 2015 14:34
 */
public class PersonDAOTest {

  public PersonDAO personDAO;
  public Mockery mockery = new JUnit4Mockery();

  @Mock
  public PersonRepository personRepository = mockery.mock(PersonRepository.class);

  @Before
  public void setUp() {
    personDAO = new PersonDAO();
  }

  @Test
  public void addPersonToDatabase() throws SQLException {

    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).addPerson(new Person(5, "Ivancho", "Ivanov", "Stoqnov"));
        will(returnValue("Person added successfully !!!"));

        oneOf(personRepository).getPerson(5);
        will(returnValue("Person [id=5, first_name=Ivancho, middle_name=Ivanov, last_name=Stoqnov"));
      }
    });
  }

  @Test
  public void deletePersonFromDatabase() throws SQLException {
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).deletePerson(3);
        will(returnValue("Person deleted from database !!!"));
      }
    });
  }

  @Test
  public void getPeopleFromDatabase() throws SQLException {
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).getPeople();
      }
    });
  }

  @Test
  public void getPersonFromDatabase() throws SQLException {
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).getPerson(3);
        will(returnValue("Person [id=3 , first_name=Sisa, middle_name=sisisi,  lastName=SIs]"));
      }
    });
  }

  @Test
  public void updatePersonToDatabase() throws SQLException {

    personDAO.updatePerson(new Person(1, "Stanimir", "Stanimir", "Stoichev"));
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).updatePerson(new Person(1, "Stanimir", "Stoichev", "Stoichev"));
        will(returnValue("Person Successfully updated !!!"));
      }
    });
//    assertEquals(personDAO.updatePerson(new Person(1, "Stanimir", "Stanimir", "Stoichev")), "Person Successfully updated !!!");
  }
}

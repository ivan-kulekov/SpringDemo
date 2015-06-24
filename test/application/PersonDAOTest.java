package application;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 23 , 2015 14:34
 */
public class PersonDAOTest {

  public PersonPersistence personDAO;
  public Mockery mockery;

  @Mock
  public PersonRepository personRepository;

  @Before
  public void setUp() {
    personDAO = new PersonPersistence();
    mockery = new JUnit4Mockery();
    personRepository = mockery.mock(PersonRepository.class);
  }

  @Test
  public void addPersonToDatabase() throws SQLException {

    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).addPerson(new Person(5, "Ivan", "Ivanov", "Stoqnov"));

        oneOf(personRepository).getPerson(5);
        will(returnValue("Person [id=5, first_name=Ivan, middle_name=Ivanov, last_name=Stoqnov"));
      }
    });
  }

  @Test
  public void deletePersonFromDatabase() throws SQLException {
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).deletePerson(3);
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
    mockery.checking(new Expectations() {
      {
        oneOf(personRepository).updatePerson(new Person(1, "Stanimir", "Stoichev", "Stoichev"));
      }
    });
  }
}

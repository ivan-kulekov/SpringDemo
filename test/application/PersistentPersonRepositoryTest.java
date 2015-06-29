package application;

import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 23 , 2015 14:34
 */
public class PersistentPersonRepositoryTest {

  public PersistentPersonRepository repository;
  public DatabaseConfig database;

  @Before
  public void setUp() throws SQLException {
    repository = new PersistentPersonRepository();
    database = new DatabaseConfig();
  }

  public void clearDataFromTableClient() throws SQLException, ClassNotFoundException {

    PreparedStatement statementClear = database.getConnection().prepareStatement("delete from client");

    statementClear.executeUpdate();
    statementClear.close();
  }

  @Test(expected = PSQLException.class)
  public void addPersonToDatabase() throws IOException, SQLException, ClassNotFoundException {

    clearDataFromTableClient();
    Person person = new Person(1, "Ivan", "Ivanov", "Ivanov");

    repository.addPerson(person);
    repository.addPerson(person);

  }

  @Test
  public void getPersonInformationFromDatabase() throws SQLException, IOException, ClassNotFoundException {

    clearDataFromTableClient();

    Person person = new Person(10, "Petko", "Petkov", "Petev");

    repository.addPerson(person);

    assertEquals(repository.getPerson(10).toString(), "Person [id=10 , first_name=Petko                                                                           ,middle_name=Petkov                                                                           ,  lastName=Petev                                                                           ]");
  }

  @Test
  public void deletePersonFromDatabase() throws IOException, SQLException, ClassNotFoundException {

    clearDataFromTableClient();
    Person person = new Person(2, "Mark", "Mirkov", "Mirkovic");
    repository.addPerson(person);
    repository.deletePerson(2);

    assertEquals(repository.getPerson(2), null);
  }


  @Test
  public void updatePersonInformationToaDatabase() throws SQLException, IOException, ClassNotFoundException {

    clearDataFromTableClient();

    Person person = new Person(13, "Penka", "Penkova", "Penkova");
    repository.addPerson(person);

    repository.updatePerson(new Person(13, "Ivanka", "Penkova", "Penkova"));

    assertEquals(repository.getPerson(13).toString(), "Person [id=13 , first_name=Ivanka                                                                          ,middle_name=Penkova                                                                          ,  lastName=Penkova                                                                         ]");
  }


  @Test
  public void getPeopleInformationFromDatabase() throws SQLException, IOException, ClassNotFoundException {

    clearDataFromTableClient();
    Person person = new Person(16, "Pavel", "Petrov", "Petkov");
    Person person1 = new Person(19, "Venci", "Vencislavov", "Venci");
    repository.addPerson(person);
    repository.addPerson(person1);

    assertEquals(repository.getPeople().toString(), "[Person [id=16 , first_name=Pavel                                                                           ,middle_name=Petrov                                                                           ,  lastName=Petkov                                                                          ], Person [id=19 , first_name=Venci                                                                           ,middle_name=Vencislavov                                                                      ,  lastName=Venci                                                                           ]]");
  }
}

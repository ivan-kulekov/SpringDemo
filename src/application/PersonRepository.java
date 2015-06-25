package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 19 , 2015 13:57
 */
public interface PersonRepository {

  void addPerson(Person person) throws SQLException, IOException;

  Person getPerson(int id) throws SQLException;

  List<Person> getPeople() throws SQLException;

  void updatePerson(Person person) throws SQLException;

  void deletePerson(int id) throws SQLException;


}

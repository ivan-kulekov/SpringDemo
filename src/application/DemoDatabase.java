package application;

import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 19 , 2015 10:10
 */
public class DemoDatabase {

  public static void main(String[] args) throws SQLException {

    PersonDAO personDAO = new PersonDAO();

/**
 * Insert Person in to a database.
 */
//    personDAO.addPerson(new Person(2,"Ivan", "Ivanov", "Ivanov"));

    /**
     * Update Person with values.
     */
//    personDAO.updatePerson(new Person(2, "kokokoo ", "Kaloqnov ", "kokoto"));

    /**
     * Print all peoples in the database.
     */
//    System.out.println(personDAO.getPeople());

    /**
     * Delete person from database.
     */

//    personDAO.deletePerson(1);

    /**
     * Get person information
     */

    System.out.println(personDAO.getPerson(3));
  }
}

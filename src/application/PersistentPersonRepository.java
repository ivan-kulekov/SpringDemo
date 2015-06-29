package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 23 , 2015 13:33
 */
public class PersistentPersonRepository implements PersonRepository {


  private DatabaseConfig database = new DatabaseConfig();

  /**
   * Make tye constructor and set up the database settings.
   */
  public PersistentPersonRepository() throws SQLException {

    try {
      database.getConnection();
    } catch (SQLException exc) {
      exc.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Add Person to the database.
   *
   * @param person is the person to add in to the database.
   * @throws SQLException
   */

  @Override
  public void addPerson(Person person) throws SQLException, IOException, ClassNotFoundException {
// //Read database request from sql script file.
//    String s;
//    StringBuffer sb = new StringBuffer();
//    try {
//      FileReader fr = new FileReader(new File("db_schema.sql"));
//
//      BufferedReader br = new BufferedReader(fr);
//
//      while ((s = br.readLine()) != null) {
//        sb.append(s);
//      }
//      br.close();
//
//
//      String[] inst = sb.toString().split(";");
//
//
//      Statement st = connection.createStatement();
//
//      for (int i = 0; i < inst.length; i++) {
//
//        if (!inst[i].trim().equals("")) {
//          st.executeUpdate(inst[i]);
//          System.out.println(">>" + inst[i]);
//        }
//      }
//
//    } catch (Exception e) {
//      System.out.println("*** Error : " + e.toString());
//      System.out.println("*** ");
//      System.out.println("*** Error : ");
//      e.printStackTrace();
//      System.out.println("################################################");
//      System.out.println(sb.toString());
//    }


    PreparedStatement statementAdd = database.getConnection().prepareStatement("INSERT INTO client VALUES(?, ?, ?, ?)");


    statementAdd.setInt(1, person.id);
    statementAdd.setString(2, person.firstName);
    statementAdd.setString(3, person.middleName);
    statementAdd.setString(4, person.lastName);


    statementAdd.executeUpdate();

    statementAdd.clearParameters();

    statementAdd.close();
  }

  /**
   * Get Just one person.
   *
   * @param id is the id on the person who get.
   * @return is the returned statement.
   * @throws SQLException
   */
  @Override
  public Person getPerson(int id) throws SQLException {

    PreparedStatement statementGetPerson = null;
    ResultSet resultSet = null;
    try {
      statementGetPerson = database.getConnection().prepareStatement("SELECT * FROM client WHERE id = ?");
      statementGetPerson.setInt(1, id);

      resultSet = statementGetPerson.executeQuery();

      while (resultSet.next()) {
        Person tempPerson = convertRowToEmployee(resultSet);
        return tempPerson;
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      close(statementGetPerson, resultSet);
    }
    return null;
  }

  /**
   * Get all people witch are in to the database.
   *
   * @return is the returned people.
   * @throws SQLException
   */

  @Override
  public List<Person> getPeople() throws SQLException {
    List<Person> listOfPersons = new ArrayList<Person>();

    PreparedStatement statementPeople = null;
    ResultSet result = null;

    try {
      statementPeople = database.getConnection().prepareStatement("select * from client");


      result = statementPeople.executeQuery();

      while (result.next()) {
        Person tempPerson = convertRowToEmployee(result);
        listOfPersons.add(tempPerson);
      }

      return listOfPersons;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      close(statementPeople, result);
    }
    return listOfPersons;
  }

  /**
   * Update concrete person.
   *
   * @param person is the person to update.
   * @throws SQLException
   */
  @Override
  public void updatePerson(Person person) throws SQLException, ClassNotFoundException {

    PreparedStatement statementUpdate = database.getConnection().prepareStatement("UPDATE client SET id = ?, first_name = ?, middle_name = ?, last_name = ? WHERE id = ?");

    statementUpdate.setInt(1, person.id);
    statementUpdate.setString(2, person.firstName);
    statementUpdate.setString(3, person.middleName);
    statementUpdate.setString(4, person.lastName);
    statementUpdate.setInt(5, person.id);

    statementUpdate.executeUpdate();

    statementUpdate.close();

  }

  /**
   * Delete Person from database.
   *
   * @param id is the id on the person who wont to delete.
   * @throws SQLException
   */
  @Override
  public void deletePerson(int id) throws SQLException, ClassNotFoundException {

    PreparedStatement statementDelete = database.getConnection().prepareStatement("DELETE FROM client WHERE id = ? ");
    statementDelete.setInt(1, id);

    statementDelete.executeUpdate();

    statementDelete.close();
  }


  /**
   * Make the Person and returned it.
   *
   * @param result is the make person.
   * @return is the returned person
   * @throws SQLException
   */
  private Person convertRowToEmployee(ResultSet result) throws SQLException {

    int id = result.getInt("id");
    String firstName = result.getString("first_name");
    String middleName = result.getString("middle_name");
    String lastName = result.getString("last_name");

    Person tempPerson = new Person(id, firstName, middleName, lastName);

    return tempPerson;
  }

  /**
   * Close the statement and result set.
   *
   * @param statementPeople is the statement , witch execute the query.
   * @param result          is the result set.
   * @throws SQLException
   */
  private void close(PreparedStatement statementPeople, ResultSet result) throws SQLException {
    close(null, statementPeople, result);
  }

  /**
   * Close the connection , statement and the result set.
   *
   * @param connection is the connection to the database.
   * @param statement  is the statement witch is execute the query.
   * @param resultSet  is the result set.
   * @throws SQLException
   */
  private void close(Connection connection, Statement statement, ResultSet resultSet)
          throws SQLException {

    if (resultSet != null) {
      resultSet.close();
    }

    if (statement != null) {
      statement.close();
    }

    if (connection != null) {
      connection.close();
    }
  }

}

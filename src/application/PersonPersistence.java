package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class PersonPersistence implements PersonRepository {
  private Connection connection = null;
  private FileInputStream input = null;

  /**
   * Make tye constructor and set up the database settings.
   */
  public PersonPersistence() {
    String dburl = "jdbc:postgresql://localhost/postgres";
    String userName = "postgres";
    String userPassword = "ivan";
    try {


      Class.forName("org.postgresql.Driver");

    } catch (ClassNotFoundException e) {

      System.out.println("Where is your PostgreSQL JDBC Driver? "
              + "Include in your library path!");
      e.printStackTrace();
      return;

    }

    System.out.println("PostgreSQL JDBC Driver Connected!");


    try {

      connection = DriverManager.getConnection(
              dburl, userName, userPassword);

    } catch (SQLException e) {

      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return;

    }

    if (connection != null) {
      System.out.println("You made it, take control your database now!");
    } else {
      System.out.println("Failed to make connection!");
    }
  }

  /**
   * Add Person to the database.
   *
   * @param person is the person to add in to the database.
   * @throws SQLException
   */

  @Override
  public void addPerson(Person person) throws SQLException, IOException {

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


    clearDataFromTableClient();
    PreparedStatement statementAdd = connection.prepareStatement("INSERT INTO client VALUES(?, ?, ?, ?)");


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
      statementGetPerson = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
      statementGetPerson.setInt(1, id);

      resultSet = statementGetPerson.executeQuery();

      while (resultSet.next()) {
        Person tempPerson = convertRowToEmployee(resultSet);
        return tempPerson;
      }
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
      statementPeople = connection.prepareStatement("select * from client");


      result = statementPeople.executeQuery();

      while (result.next()) {
        Person tempPerson = convertRowToEmployee(result);
        listOfPersons.add(tempPerson);
      }

      return listOfPersons;
    } finally {
      close(statementPeople, result);
    }
  }

  /**
   * Update concrete person.
   *
   * @param person is the person to update.
   * @throws SQLException
   */
  @Override
  public void updatePerson(Person person) throws SQLException {

    PreparedStatement statementUpdate = connection.prepareStatement("UPDATE client SET id = ?, first_name = ?, middle_name = ?, last_name = ? WHERE id = ?");

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
  public void deletePerson(int id) throws SQLException {

    PreparedStatement statementDelete = connection.prepareStatement("DELETE FROM client WHERE id = ? ");
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

  private void clearDataFromTableClient() throws SQLException {

    PreparedStatement statementClear = connection.prepareStatement("delete from client");

    statementClear.executeUpdate();
    statementClear.close();
  }

}

package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since Jun 29 , 2015 10:51
 */
public class DatabaseConfig {


  private static Connection con;
  String dburl = "jdbc:postgresql://localhost/postgres";
  String userName = "postgres";
  String userPassword = "ivan";

  public Connection getConnection() throws SQLException, ClassNotFoundException {

    try {

      Class.forName("org.postgresql.Driver");

    } catch (ClassNotFoundException e) {

      System.out.println("Where is your PostgreSQL JDBC Driver? "
              + "Include in your library path!");
      e.printStackTrace();


    }

    System.out.println("PostgreSQL JDBC Driver Connected!");

    try {
      con = DriverManager.getConnection(
              dburl, userName, userPassword);

    } catch (SQLException e) {
      e.printStackTrace();


    }

    if (con != null) {
      System.out.println("You made it, take control your database now!");
    } else {
      System.out.println("Failed to make connection!");
    }
    return con;
  }
}


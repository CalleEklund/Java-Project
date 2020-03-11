import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Remote database: https://remotemysql.com/databases.php
 * phpMyAdmin: https://remotemysql.com/phpmyadmin/index.php
 * Help: https://www3.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
 */
public class DatabaseTest
{

    static Connection conn = null;

    public static void main(String[] args) throws SQLException {
	try {
	    String url = "jdbc:mysql://remotemysql.com:3306/tMGM8IRhyq";
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(url, "tMGM8IRhyq", "oLJpQFeIgY");
	} catch (ClassNotFoundException | SQLException e) {
	    e.printStackTrace();
	}

//	insert();
	selectAll();

    }

    public static void selectAll() throws SQLException {
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * from test_table");

	while (rs.next()) {
	    System.out.println(rs.getString("name"));
	}
    }

    public static void insert() throws SQLException {
	Statement stmt = conn.createStatement();
//	ResultSet rs = stmt.executeQuery();
	String query = "INSERT INTO test_table (name,password)VALUES ('Emma','losen2')";
	int countInserted = stmt.executeUpdate(query);
	System.out.println(countInserted + " records inserted.\n");

    }
}

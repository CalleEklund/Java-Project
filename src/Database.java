import classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    private Connection conn = null;
    private String url = "jdbc:mysql://remotemysql.com:3306/tMGM8IRhyq";
    private String user = "tMGM8IRhyq";
    private String password = "oLJpQFeIgY";


    public Database() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(url, user, password);
	} catch (ClassNotFoundException | SQLException e) {
	    e.printStackTrace();
	}
    }

    public void insertUser(User u) {
	String name = u.getName();
	String email = u.getEmail();
	String password = u.getPassword();

	try {
	    String query = "INSERT INTO user (user.name ,user.password,user.email)" + "VALUES (?,?,?)";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);

	    preparedStmt.setString(1, name);
	    preparedStmt.setString(2, email);
	    preparedStmt.setString(3, password);

	    preparedStmt.execute();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    //Sätter id till 1
    public void resetAI() {
	try {
	    Statement stmt = conn.createStatement();
	    String query = "ALTER TABLE user AUTO_INCREMENT=0";
	    stmt.execute(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	Database db = new Database();

        db.insertUser(new User("calletest","test@gmail.com","testlosen"));
//	db.resetAI();
    }
}

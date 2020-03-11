package texthandlers;

import classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    /**
     * TODO: Funktioner:
     * - Lägga in användare KLAR
     * - Kolla om användare finns KLAR
     * - Hämta specifik användare
     * - Lägg till lån
     */
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

    public boolean userExists(User u) {
	String email = u.getEmail();
	String query = "SELECT * FROM user WHERE email = ?";

	ResultSet rs;
	try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString(1, email);
	    rs = preparedStmt.executeQuery();
	    if (!rs.next()) {
		System.out.println("No result found");
		return false;
	    } else {
		do {
		    System.out.println("result " + rs.getString("name"));
		    return true;
		} while (rs.next());
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return false;

    }

    public void insertUser(User u) {
	String name = u.getName();
	String email = u.getEmail();
	String password = u.getPassword();

	String query = "INSERT INTO user (user.name ,user.password,user.email)" + "VALUES (?,?,?)";
	try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);

	    preparedStmt.setString(1, name);
	    preparedStmt.setString(2, password);
	    preparedStmt.setString(3, email);

	    preparedStmt.execute();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    /**
     *Lägg till så att lån läggas till också
     */
    public void getUser(String email){
        if(userExists(new User(email))){
            String query = "SELECT * FROM user WHERE email = ?";
            try{
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1,email);
		ResultSet rs =preparedStmt.executeQuery();

		while(rs.next()){
		    String nameDB = rs.getString("name");
		    String emailDB = rs.getString("email");
		    String password = "";
//		    System.out.println(+"\n"++"\n"+rs.getString("password"));
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	}else{
	    System.out.println("no user");
//            return new User();
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
	User tu = new User("calletest", "test@gmail.com", "testlosen");
	User wu = new User("calletest", "no@gmail.com", "testlosen");

//	db.insertUser(tu);
//	db.resetAI();
//	System.out.println(db.userExists(tu));
	db.getUser(wu.getEmail());
    }
}

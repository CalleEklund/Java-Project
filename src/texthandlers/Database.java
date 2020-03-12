package texthandlers;

import classes.Loan;
import classes.User;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Database
{
    /**
     * * Remote database: https://remotemysql.com/databases.php
     * * phpMyAdmin: https://remotemysql.com/phpmyadmin/index.php *
     * Help: https://www3.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
     */
    private Connection conn = null;
    private static String url = "jdbc:mysql://remotemysql.com:3306/tMGM8IRhyq";
    private static String user = "tMGM8IRhyq";
    private static String password = "oLJpQFeIgY";


    public Database() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(url, user, password);
	} catch (ClassNotFoundException | SQLException e) {
	    e.printStackTrace();
	}
    }

    public boolean userExists(String userEmail) {
	String query = "SELECT * FROM user WHERE email = ?";

	ResultSet rs;
	try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString(1, userEmail);
	    rs = preparedStmt.executeQuery();
	    if (!rs.next()) {
		System.out.println("No result found");
		return false;
	    } else {
		do {
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
     * Lägg till så att lån läggas till också query SELECT loan.title FROM loan INNER JOIN user ON loan.user_id = user.id WHERE
     * user.email = MAIL
     *
     * @return
     */
    public User getUser(String email) {
	if (userExists(email)) {
	    String query = "SELECT * FROM user WHERE email = ?";
	    String idDB;
	    String nameDB;
	    String emailDB;
	    String passwordDB;
	    ArrayList<Loan> userLoansDB = convertToLoan(email);
	    try {
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, email);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {

		    idDB = rs.getString("user.id");
		    nameDB = rs.getString("user.name");
		    emailDB = rs.getString("user.email");
		    passwordDB = rs.getString("user.password");

		    return new User(idDB, nameDB, emailDB, passwordDB, userLoansDB);
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	}
	return null;
    }

    public ArrayList<Loan> convertToLoan(String email) {
	String query = "SELECT loan.* FROM loan INNER JOIN user ON loan.user_id = user.id WHERE user.email = ?";
	ArrayList<Loan> temp = new ArrayList<>();

	String title;
	String desc;
	double intrest;
	int amount;
	int amortization;
	LocalDate start;
	LocalDate end;
	try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString(1, email);
	    ResultSet rs = preparedStmt.executeQuery();
	    while (rs.next()) {
		title = rs.getString("title");
		desc = rs.getString("description");
		intrest = rs.getDouble("intrest");
		amount = rs.getInt("amount");
		amortization = rs.getInt("amortization");

		start = rs.getDate("startdate").toLocalDate();
		end = rs.getDate("enddate").toLocalDate();
		Loan current = new Loan(title, desc, intrest, amount, amortization, start, end);

		temp.add(current);

	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return temp;
    }

    public void addLoanToUser(User u, Loan l) {
	String userId = getUser(u.getEmail()).getUid();

	String title = l.getTitle();
	String desc = l.getDescription();
	double intrest = l.getIntrest();
	int amount = l.getAmount();
	int amortization = l.getAmortization();
	Date start = Date.valueOf(l.getStartDate());
	Date end = Date.valueOf(l.getEndDate());

	String query = "INSERT INTO loan (user_id,title,loan.description,intrest,amount,amortization,startdate,enddate)" +
		       " VALUES (?,?,?,?,?,?,?,?)";
	try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString(1, userId);
	    preparedStmt.setString(2, title);
	    preparedStmt.setString(3, desc);
	    preparedStmt.setDouble(4, intrest);
	    preparedStmt.setInt(5, amount);
	    preparedStmt.setInt(6, amortization);
	    preparedStmt.setDate(7, start);
	    preparedStmt.setDate(8, end);

	    preparedStmt.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    //Sätter id till 1
    public void resetAI() {
	try {
	    Statement stmt = conn.createStatement();
	    String queryuser = "ALTER TABLE user AUTO_INCREMENT=0";
	    stmt.execute(queryuser);
	    String queryloan = "ALTER TABLE loan AUTO_INCREMENT=0";
	    stmt.execute(queryloan);

	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	Database db = new Database();
	User tu = new User("calletest", "test@gmail.com", "testlosen");
	User wu = new User("calletest", "no@gmail.com", "testlosen");

	Loan tl = new Loan("testdb", "testdb", 10, 100, 1, LocalDate.of(2000, 10, 15), LocalDate.now());
	Loan t2 = new Loan("testdb2", "testdb", 10, 100, 1, LocalDate.of(2000, 10, 15), LocalDate.now());
	Loan t3 = new Loan("testdb3", "testdb", 10, 100, 1, LocalDate.of(2000, 10, 15), LocalDate.now());
//	db.insertUser(wu);
//	db.resetAI();
//	System.out.println(db.userExists(tu));
//	db.addLoanToUser(wu, t2);
	System.out.println(db.getUser(tu.getEmail()));
    }
}

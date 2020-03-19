package texthandlers;

import classes.Loan;
import classes.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class Database
{
    /**
     * * Remote database: https://remotemysql.com/databases.php * phpMyAdmin: https://remotemysql.com/phpmyadmin/index.php *
     * Help: https://www3.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
     */
    private Connection conn = null;
    private static String url = "jdbc:mysql://remotemysql.com:3306/tMGM8IRhyq";


    public Database() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    try {
		final String user = "tMGM8IRhyq";
		final String password = "oLJpQFeIgY";
		this.conn = getConnection(url, user, password);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }


    public boolean userExists(String userEmail) {

	try {
	    final String query = "SELECT * FROM user WHERE email = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    try {
		preparedStmt.setString(1, userEmail);
		try {
		    ResultSet rs = preparedStmt.executeQuery();
		    try {
			while (rs.next()) {
			    String emailDB = rs.getString("email");
			    if (emailDB.equals(userEmail)) {
				return true;
			    }
			}
			return false;
		    } finally {
			rs.close();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    } finally {
		preparedStmt.close();
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

	final String query = "INSERT INTO user (user.name ,user.password,user.email)" + "VALUES (?,?,?)";

	try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {

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
	ArrayList<Loan> userLoansDB = convertToLoan(email);
	if (userExists(email)) {
	    try {
		final String query = "SELECT * FROM user WHERE email = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try {
		    preparedStmt.setString(1, email);
		    try {
			ResultSet rs = preparedStmt.executeQuery();
			try {
			    while (rs.next()) {
				String idDB = rs.getString("user.id");
				String nameDB = rs.getString("user.name");
				String emailDB = rs.getString("user.email");
				String passwordDB = rs.getString("user.password");
				User u = new User(emailDB, passwordDB, idDB, nameDB, userLoansDB);
				return u;
			    }
			} finally {
			    rs.close();
			}
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		} finally {
		    preparedStmt.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	}

	return null;
    }

    public ArrayList<Loan> convertToLoan(String email) {
	ArrayList<Loan> temp = new ArrayList<>();
	try {
	    final String query = "SELECT loan.* FROM loan INNER JOIN user ON loan.user_id = user.id WHERE user.email = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    try {
		preparedStmt.setString(1, email);
		try {
		    ResultSet rs = preparedStmt.executeQuery();

		    try {
			while (rs.next()) {
			    String title = rs.getString("title");
			    String desc = rs.getString("description");
			    double intrest = rs.getDouble("intrest");
			    int amount = rs.getInt("amount");
			    int amortization = rs.getInt("amortization");

			    LocalDate start = rs.getDate("startdate").toLocalDate();
			    LocalDate end = rs.getDate("enddate").toLocalDate();
			    Loan current = new Loan(title, desc, intrest, amount, amortization, start, end);

			    temp.add(current);

			}
		    } finally {
			rs.close();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    } finally {
		preparedStmt.close();
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return temp;
    }

    public void saveLoanToUser(User u, Loan l) {
	String userId = u.getUid();

	String title = l.getTitle();
	String desc = l.getDescription();
	double intrest = l.getIntrest();
	int amount = l.getAmount();
	int amortization = l.getAmortization();
	Date start = Date.valueOf(l.getStartDate());
	Date end = Date.valueOf(l.getEndDate());

	try {
	    final String query =
		    "INSERT INTO loan (user_id,title,loan.description,intrest,amount,amortization,startdate,enddate)" +
		    " VALUES (?,?,?,?,?,?,?,?)";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    try {
		preparedStmt.setString(1, userId);
		preparedStmt.setString(2, title);
		preparedStmt.setString(3, desc);
		preparedStmt.setDouble(4, intrest);
		preparedStmt.setInt(5, amount);
		preparedStmt.setInt(6, amortization);
		preparedStmt.setDate(7, start);
		preparedStmt.setDate(8, end);

		preparedStmt.execute();

	    } finally {
		preparedStmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    //Sätter id till 1
    public void resetAI() {
	try {
	    Statement stmt = conn.createStatement();
	    try {
		final String queryuser = "ALTER TABLE user AUTO_INCREMENT=0";
		stmt.execute(queryuser);
		final String queryloan = "ALTER TABLE loan AUTO_INCREMENT=0";
		stmt.execute(queryloan);
	    } finally {
		stmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }
}

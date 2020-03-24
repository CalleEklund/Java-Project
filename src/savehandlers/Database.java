package savehandlers;

import classes.Loan;
import classes.User;
import classes.UserTypes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class Database
{
    /**
     * DATABAS HJÄLP LÄNKAR:
     * Remote database: https://remotemysql.com/databases.php
     * phpMyAdmin: https://remotemysql.com/phpmyadmin/index.php
     */
    private Connection conn = null;
    private static String url = "jdbc:mysql://remotemysql.com:3306/tMGM8IRhyq";

    /**
     * Konstruktor
     * Används för att skapa en koppling mot databasen, fånger evetuella fel.
     */
    public Database() {
	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    try {
		final String user = "tMGM8IRhyq";
		final String password = "oLJpQFeIgY";
		this.conn = getConnection(url, user, password);
	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Kollar om den sökta användaren finns i database, fånger evetuella fel samt
     * stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @param userEmail Den sökta användarens email
     * @param userPassword Den sökta användarens lösenord
     * @return (boolean) om användaren finns eller inte
     */
    public boolean userExists(String userEmail, String userPassword) {
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
			    String passwordDB = rs.getString("password");
			    if (emailDB.equals(userEmail) && passwordDB.equals(userPassword)) {
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

    /**
     * Lägger in en användare till databasen, fånger evetuella fel samt
     * stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @param u Användaren från User klassen
     */
    public void insertUser(User u) {
	String name = u.getName();
	String email = u.getEmail();
	String password = u.getPassword();
	int userType;
	if (u.getUserType().equals(UserTypes.ORDINARY)) {
	    userType = 0;
	} else {
	    userType = 1;
	}

	try {
	    final String query = "INSERT INTO user (user.name ,user.password,user.email, user.userType)" + "VALUES (?,?,?,?)";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    try {
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, password);
		preparedStmt.setString(3, email);
		preparedStmt.setInt(4, userType);

		preparedStmt.execute();
	    } finally {
		preparedStmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Hämtar användare från databasen.
     * Warning (is overly nested): behövs för att kunna stänga PreparedStatement samt Resultset och fånga felhanteringen
     *
     * @param email    användarens email
     * @param password användarens lösenord
     * @return
     */
    public User getUser(String email, String password) {
	ArrayList<Loan> userLoansDB = convertToLoan(email);
	if (userExists(email, password)) {
	    try {
		final String query = "SELECT * FROM user WHERE email = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try {
		    preparedStmt.setString(1, email);
		    try {
			ResultSet rs = preparedStmt.executeQuery();
			try {
			    while (rs.next()) {
				User u;
				String idDB = rs.getString("user.id");
				String nameDB = rs.getString("user.name");
				String emailDB = rs.getString("user.email");
				String passwordDB = rs.getString("user.password");
				UserTypes userType;
				if (rs.getInt("user.userType") == 0) {
				    userType = UserTypes.ORDINARY;
				    u = new User(emailDB, passwordDB, idDB, nameDB, userLoansDB, userType);

				} else {
				    userType = UserTypes.ADMIN;
				    u = new User(emailDB, passwordDB, userType);
				}
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

    /**
     * Hämtar användares lån från databasen med en one-to-many relation och konverterar lån till en ArrayList(Loan),
     * fånger evetuella fel samt stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @param email Den sökta användarens email, använder email istället för id eftersom email också är unikt för varje användare
     * @return Användarens lån i form av ArrayList(Loan)
     */
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

    /**
     * Lägger till lån i databas samt skapar koppling till användaren, fånger evetuella fel samt
     * stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @param u Den sökta användaren
     * @param l Den valda lånet som användaren vill lägga till
     */
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

    /**
     * Hämtar all användare data från användre som registrerat sig som ORDINARY användare, används enbart i adminsidan
     * för att kunna monitera samt ändra användarens inloggnings uppgifter, fånger evetuella fel samt
     * stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @return En List(User) av alla ORDINARY användare
     */
    public List<User> getAllData() {
	List<User> out = new ArrayList<>();

	try {
	    Statement stmt = conn.createStatement();
	    try {
		final String query = "SELECT * FROM user";
		ResultSet rs = stmt.executeQuery(query);
		try {
		    while (rs.next()) {
			if (rs.getInt("userType") == 0) {
			    String email = rs.getString("user.email");
			    ArrayList<Loan> userLoans = convertToLoan(email);
			    User u =
				    new User(rs.getString("user.email"), rs.getString("user.password"), rs.getString("user.id"),
					     rs.getString("user.name"), userLoans, UserTypes.ORDINARY);
			    out.add(u);
			}
		    }
		} finally {
		    rs.close();
		}
	    } finally {
		stmt.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return out;
    }

    /**
     * Uppdaterar datan som är ändrar i adminsidan (AdminPage), fånger evetuella fel samt
     * stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     * @param newData Den ändrade data
     */
    public void updateData(List<User> newData) {
	for (int i = 0; i < newData.size(); i++) {
	    try {
		String query = "UPDATE user set name = ?," + "email = ?," + "password = ?" + "WHERE email = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try {
		    preparedStmt.setString(1, newData.get(i).getName());
		    preparedStmt.setString(2, newData.get(i).getEmail());
		    preparedStmt.setString(3, newData.get(i).getPassword());
		    preparedStmt.setString(4, newData.get(i).getEmail());

		    preparedStmt.execute();

		} finally {
		    preparedStmt.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	System.out.println(newData);
    }

    /**
     * Används inte i applikationen utan mer som en hjälp funktion ifall man skulle vilja resetta id:n i user tabellen
     */
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

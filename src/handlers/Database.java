package handlers;

import user_loan_classes.Loan;
import user_loan_classes.User;
import user_loan_classes.UserType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static java.sql.DriverManager.getConnection;


/**
 * Databas klassen som hanterar all koppling med databasen, det finns extra många try-catches för att kunna fånga fel som finns
 * i statements som i preparedstatement även i själva resultatet alltså resultsetet.
 */
public class Database
{
    private Connection conn = null;
    private String url = "jdbc:mysql://localhost/tddd78?useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
    private ProjectLogger databaseProjectLogger;

    /**
     * Konstruktor Används för att skapa en koppling mot databasen, fånger evetuella fel. Warning (Call to
     * 'DriverManager.getConnection()'): Hittar ingen lösning på detta men det är inget som påverkar programmet.
     */
    public Database(ProjectLogger projectLogger) {
	databaseProjectLogger = projectLogger;
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    try {
		final String user = "root";
		final String password = "";
		this.conn = getConnection(url, user, password);
	    } catch (SQLException e) {
		nullDatabase();
		conn.close();
		databaseProjectLogger.logMsg(Level.SEVERE, "Gick inte att koppla till databasen");
		e.printStackTrace();
	    }

	} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte hitta databas drivers");
	    e.printStackTrace();
	}
    }

    /**
     * Anropas om databas koppling inte finns (=null), användaren får en dialog ruta där det förklaras att ingen databaskoppling
     * finns och programmet avstängs eftersom det inte finns någon funktion som inte användare en databas.
     */
    private void nullDatabase() {
	if (conn == null) {
	    Object[] options = { "OK" };
	    int result = JOptionPane
		    .showOptionDialog(null, "Det finns ingen databaskoppling", "Databas Error", JOptionPane.PLAIN_MESSAGE,
				      JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    if (result == 0) {
		System.exit(0);
	    }
	}
    }

    /**
     * Kollar om den sökta användaren finns i database, fånger evetuella fel samt stänger kopplingen mot databasen för att inte
     * skapa dubbelskrivningar.
     *
     * @param userEmail    Den sökta användarens email
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
		    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett Resultset -> felaktig PreparedStatement");
		    e.printStackTrace();
		}
	    } finally {
		preparedStmt.close();
	    }
	} catch (SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");

	    e.printStackTrace();
	}
	return false;
    }

    /**
     * Lägger in en användare till databasen, fånger evetuella fel samt stänger kopplingen mot databasen för att inte skapa
     * dubbelskrivningar.
     *
     * @param u Användaren från User klassen
     */
    public void insertUser(User u) {
	String name = u.getName();
	String email = u.getEmail();
	String password = u.getPassword();
	int userType;
	if (u.getUserType().equals(UserType.ORDINARY)) {
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
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");

	    e.printStackTrace();
	}
    }

    /**
     * Hämtar användare från databasen. Warning (is overly nested): behövs för att kunna stänga PreparedStatement samt Resultset
     * och fånga felhanteringen
     *
     * @param email    användarens email
     * @param password användarens lösenord
     * @return användare och dess lån
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
				UserType userType;
				if (rs.getInt("user.userType") == 0) {
				    userType = UserType.ORDINARY;
				    u = new User(emailDB, passwordDB, idDB, nameDB, userLoansDB, userType);

				} else {
				    userType = UserType.ADMIN;
				    u = new User(nameDB, emailDB, passwordDB, userType);
				}
				return u;
			    }
			} finally {
			    rs.close();
			}
		    } catch (SQLException e) {
			databaseProjectLogger
				.logMsg(Level.SEVERE, "Kunde inte skapa ett Resultset -> felaktig PreparedStatement");
			e.printStackTrace();
		    }
		} finally {
		    preparedStmt.close();
		}
	    } catch (SQLException e) {
		databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");

		e.printStackTrace();
	    }

	}

	return null;
    }

    /**
     * Hämtar användares lån från databasen med en one-to-many relation och konverterar lån till en ArrayList(Loan), fånger
     * evetuella fel samt stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     *
     * @param email Den sökta användarens email, använder email istället för id eftersom email också är unikt för varje
     *              användare
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
		    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett Resultset -> felaktig PreparedStatement");
		    e.printStackTrace();
		}
	    } finally {
		preparedStmt.close();
	    }

	} catch (SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");
	    e.printStackTrace();
	}
	return temp;
    }

    /**
     * Lägger till lån i databas samt skapar koppling till användaren, fånger evetuella fel samt stänger kopplingen mot
     * databasen för att inte skapa dubbelskrivningar.
     *
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
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");
	    e.printStackTrace();
	}
    }

    /**
     * Hämtar all användare data från användre som registrerat sig som ORDINARY användare, används enbart i adminsidan för att
     * kunna monitera samt ändra användarens inloggnings uppgifter, fånger evetuella fel samt stänger kopplingen mot databasen
     * för att inte skapa dubbelskrivningar.
     *
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
					     rs.getString("user.name"), userLoans, UserType.ORDINARY);
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
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett Statement -> felaktig i databas kopplingen");

	    e.printStackTrace();
	}
	return out;
    }

    /**
     * Uppdaterar datan som är ändrad i adminsidan (AdminPage), om det inte går att uppdatera användaren så skapas en ny. Fångar
     * evetuella fel samt stänger kopplingen mot databasen för att inte skapa dubbelskrivningar.
     *
     * @param newData Den ändrade data
     */
    public void updateData(List<User> newData) {
	for (User newDatum : newData) {
	    try {
		final String query = "REPLACE INTO user (id,name,email,password) VALUES (?,?,?,?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		try {
		    preparedStmt.setString(1, newDatum.getUid());
		    preparedStmt.setString(2, newDatum.getName());
		    preparedStmt.setString(3, newDatum.getEmail());
		    preparedStmt.setString(4, newDatum.getPassword());

		    preparedStmt.execute();

		} finally {
		    preparedStmt.close();
		}
	    } catch (SQLException e) {
		databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");

		e.printStackTrace();
	    }
	}

    }

    /**
     * Tar bort en användare samt deras lån från databasen. Fångar evetuella fel samt stänger kopplingen mot databasen för att
     * inte skapa dubbelskrivningar.
     *
     * @param u den sökta användare önskad att radera
     */
    public void removeUser(User u) {
	try {
	    final String query = "DELETE from user where email = ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    try {
		preparedStmt.setString(1, u.getEmail());
		preparedStmt.execute();
	    } finally {
		preparedStmt.close();
	    }
	} catch (SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Kunde inte skapa ett PrepareStatement -> felaktig input i queryn");

	    e.printStackTrace();
	}
    }

    /**
     * Stänger databas kopplingen samt fångar eventuella fel
     */
    private void closeConnection() {
	try {
	    conn.close();
	} catch (SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Fel vi kopplingen av databasen");
	    e.printStackTrace();
	}

    }

    /**
     * Används inte i applikationen utan mer som en hjälp funktion ifall man skulle vilja resetta id:n i user tabellen
     */
    public void resetAI() {
	try {
	    Statement stmt = conn.createStatement();
	    try {
		final String queryUser = "ALTER TABLE user AUTO_INCREMENT=0";
		stmt.execute(queryUser);
		final String queryLoan = "ALTER TABLE loan AUTO_INCREMENT=0";
		stmt.execute(queryLoan);
	    } finally {
		stmt.close();
	    }
	} catch (SQLException e) {
	    databaseProjectLogger.logMsg(Level.SEVERE, "Fel vi kopplingen av databasen");
	    e.printStackTrace();
	}

    }
}

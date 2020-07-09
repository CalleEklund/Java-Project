package user_loan_classes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Användare klassen som skapas vid kontoskapningen, även klassen som används vid lagring samt hämtning med databas. Klassen har
 * flear konstruktorer eftersom en User klass ska skapas på olika sätt bland annat att en admin skapar en ny användare via
 * adminklassen men även om en "vanlig" användare skapar en med ett nytt konto. Även en tom konstruktor som ersätter den
 * nuvarande inloggade användare vid en utloggning.
 */
public class User extends AbstractUser
{
    private String uid = null;
    private ArrayList<Loan> userLoans = null;
    private UserType userType = null;


    public User(String name, String email, String password, final UserType userType) {
	super(email, password,name);
	this.userType = userType;

    }


    public User(final String emailDB, final String passwordDB, final String uid, final String name,
		final ArrayList<Loan> userLoans, final UserType userTypeDB)
    {
	super(emailDB, passwordDB,name);
	this.uid = uid;
	this.userLoans = userLoans;
	this.userType = userTypeDB;

    }

    public User(final String id, final String name, final String email, final String password, final UserType userType) {
	super(email, password,name);
	this.uid = id;
	this.userType = userType;

    }


    public User() {
	super();
	this.userLoans = new ArrayList<>();
	this.userType = UserType.ORDINARY;
    }

    /**
     * Getters
     **/
    public String getUid() {
	return uid;
    }

    public void addUserLoan(Loan l) {
	userLoans.add(l);
    }

    public ArrayList<Loan> getUserLoans() {
	return userLoans;
    }

    public UserType getUserType() {
	return userType;
    }

    /**
     * metoden equals kollar om lösenord samt email är lika
     **/
    @Override public boolean equals(final Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	final User user = (User) o;
	return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override public int hashCode() {
	return Objects.hash(email, password);
    }

    /**
     * Används för att jämföra användare.
     * @param u en Användare från User klassen
     * @return (Boolean) Beroende på om användare är exakt samma användare.
     */
    public boolean compareTo(final User u) {
	return uid == u.uid && name == u.name && email == u.email && password == u.password && userType == u.userType;
    }

    @Override public String toString() {
	return "User{" + "uid='" + uid + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" +
	       password + '\'' + ", userType=" + userType + ", userLoans=" + userLoans + '}';
    }
}

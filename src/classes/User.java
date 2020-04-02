package classes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Användare
 */
public class User extends AbstractUser
{
    private String uid = null;
    private String name = null;
    private ArrayList<Loan> userLoans = null;
    private UserTypes userType = null;

    //Skapa konto konstruktorer

    public User(String name, String email, String password, final UserTypes userType) {
	super(email, password);
	this.name = name;
	this.userType = userType;

    }


    public User(final String emailDB, final String passwordDB, final String uid, final String name,
		final ArrayList<Loan> userLoans, final UserTypes userTypeDB)
    {
	super(emailDB, passwordDB);
	this.uid = uid;
	this.name = name;
	this.userLoans = userLoans;
	this.userType = userTypeDB;

    }

    public User(final String id, final String name, final String email, final String password, final UserTypes userType) {
	super(email, password);
	this.name = name;
	this.uid = id;
	this.userType = userType;

    }


    public User() {
	super();
	this.name = "";
	this.userLoans = new ArrayList<>();
	this.userType = UserTypes.ORDINARY;
    }

    /**
     * Getters
     **/
    public String getUid() {
	return uid;
    }

    public String getName() {
	return name;
    }

    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }

    public void addUserLoan(Loan l) {
	userLoans.add(l);
    }

    public ArrayList<Loan> getUserLoans() {
	return userLoans;
    }

    public UserTypes getUserType() {
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

    public boolean compareTo(final User u) {
	return uid == u.uid && name == u.name && email == u.email && password == u.password && userType == u.userType;
    }

    @Override public String toString() {
	return "User{" + "uid='\"" + uid + ", name='" + name + '\'' + ", userLoans=" + userLoans + ", userType=" + userType +
	       '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}

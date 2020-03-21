package classes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Användare
 */
public class User extends AbstractUser
{
    private String name = null;
    private ArrayList<Loan> userLoans = null;
    private UserTypes userType = null;

    public User(final String uid, final String email, final String password, final String name)
    {
	super(uid, email, password);
	this.name = name;
	this.userType = UserTypes.ORDINARY;
    }

    public User(String name, String email, String password) {
	super(email, password);
	this.name = name;
	this.userLoans = new ArrayList<>();
	this.userType = UserTypes.ORDINARY;

    }

    public User(final String emailDB, final String passwordDB, final String uid, final String name,
		final ArrayList<Loan> userLoans)
    {
	super(emailDB, passwordDB);
	this.name = name;
	this.userLoans = userLoans;
	this.userType = UserTypes.ORDINARY;

    }
    //Admin User
    public User(String email, String password) {
	super(email, password);
	this.userType = UserTypes.ADMIN;
    }

    public User() {
	super();
	this.name = "";
	this.userLoans = new ArrayList<>();
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


    @Override public String toString() {
	return "User{" + "uid='" + uid + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" +
	       password + '\'' + ", userLoans=" + userLoans + '}';
    }
}

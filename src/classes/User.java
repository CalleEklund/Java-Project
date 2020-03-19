package classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Användare
 */
public class User extends AbstractUser
{
    private String uid = null;
    private String name;
    private ArrayList<Loan> userLoans = null;

    public User(final String uid, final String email, final String password, final String name, final String email1,
		final String password1)
    {
	super(uid, email, password);
	this.name = name;
    }

    public User(String name, String email, String password) {
	super(email, password);
	this.uid = UUID.randomUUID().toString();
	this.name = name;
	this.userLoans = new ArrayList<>();
    }
    public User(final String emailDB, final String passwordDB, final String uid, final String name, final ArrayList<Loan> userLoans)
    {
	super(emailDB, passwordDB);
	this.uid = uid;
	this.name = name;
	this.userLoans = userLoans;
    }

    public User() {
        super();
	this.uid = "";
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

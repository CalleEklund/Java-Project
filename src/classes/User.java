package classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Användare
 */
public class User
{
    private String uid;
    private String name;
    private String email;
    private String password;
    private ArrayList<Loan> userLoans;

    public User(String name, String email, String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = name;
	this.email = email;
	this.password = password;
	this.userLoans = new ArrayList<>();
    }
    public User(final String idDB, final String nameDB, final String emailDB, final String passwordDB, final ArrayList<Loan> userLoansDB) {
	this.uid = idDB;
	this.name = nameDB;
	this.email = emailDB;
	this.password = passwordDB;
	this.userLoans = userLoansDB;
    }
    //login konstruktor, bara misc.test
    public User(String email,String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = "testanvändare";
	this.email = email;
	this.password = password;
	this.userLoans = new ArrayList<>();

    }

    public User(String email) {
	this.uid = "";
	this.name = "";
	this.email = email;
	this.password = "";
	this.userLoans = new ArrayList<>();
    }

    public User() {
	this.uid = "";
	this.name = "";
	this.email = "";
	this.password = "";
	this.userLoans = new ArrayList<>();
    }
    /**
     * Setters och getters
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

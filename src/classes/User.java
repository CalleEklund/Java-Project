package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class User
{
    private String uid, name, email, password;
    private ArrayList<Loan> userLoans;

    public User(String name, String email, String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = name;
	this.email = email;
	this.password = password;
	this.userLoans = new ArrayList<>();
    }

    //login konstruktor, bara test
    public User(String email, String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = "testanvändare";
	this.email = email;
	this.password = password;
	this.userLoans = new ArrayList<>();

    }

    public User(String uid, String name, String email, String password, ArrayList userLoans) {
	this.uid = uid;
	this.name = name;
	this.email = email;
	this.password = password;
	this.userLoans = userLoans;
    }

    public User() {
    }

    public String getUid() {
	return uid;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void addUserLoan(Loan l) {
	userLoans.add(l);
    }

    public ArrayList<Loan> getUserLoans() {
	return userLoans;
    }
    public boolean equals(final User o) {
	return Objects.equals(password, o.password) && Objects.equals(email, o.email);
    }

    @Override public String toString() {
	return "User{" + "uid='" + uid + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" +
	       password + '\'' + ", userLoans=" + userLoans + '}';
    }
}

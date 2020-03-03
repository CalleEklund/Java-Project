package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class User
{
    private String uid, name, email, password;
    private HashMap<String, Loan> userLoanshm;

    public User(String name, String email, String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = name;
	this.email = email;
	this.password = password;
	this.userLoanshm = new HashMap<String, Loan>();
    }

    //login konstruktor, bara test
    public User(String email, String password) {
	this.uid = UUID.randomUUID().toString();
	this.name = "testanvändare";
	this.email = email;
	this.password = password;
	this.userLoanshm = new HashMap<String, Loan>();

    }
    public User(String uid,String name, String email, String password,HashMap userLoanshm) {
	this.uid = uid;
	this.name = name;
	this.email = email;
	this.password = password;
	this.userLoanshm = userLoanshm;
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

    public void addUserLoanshm(Loan l) {
	if (l != null) {
	    userLoanshm.put(l.getUid(), l);
	}
    }

    public void removeUserLoanhm(String loanId) {
	userLoanshm.remove(loanId);
    }

    public HashMap<String, Loan> getUserLoanshm() {
	return userLoanshm;
    }

//    @Override public String toString() {
//	return "name=" + name + "\n, email='" + email + "\n, password=" + password + "\nloans=" +
//	       userLoanshm;
//    }

//    public static void main(String[] args) {
//        classes.User test = new classes.User("calle","carek123@student.liu.se","losen123");
//        test.addLoans(new classes.Loan("test","test",0.12,1000, LocalDate.of(2000,10,15),LocalDate.now()));
//        System.out.println(test);
//    }
}

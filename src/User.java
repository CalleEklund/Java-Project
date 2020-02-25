import java.util.ArrayList;
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

    public User(String email, String password) {
        this.uid = UUID.randomUUID().toString();
	this.name = "testanvändare";
	this.email = email;
	this.password = password;
	this.userLoans = new ArrayList<>();
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

    public String getUserLoans() {
	return userLoans.toString();
    }

    public void addLoan(Loan l) {
	this.userLoans.add(l);
    }

    public void removeLoans(Loan l) {
	this.userLoans.remove(l);
    }

    @Override public String toString() {
	return "uid=" + uid + "\n, name=" + name + "\n, email='" + email + "\n, password=" + password + "\nloans=" +
	       userLoans;
    }

//    public static void main(String[] args) {
//        User test = new User("calle","carek123@student.liu.se","losen123");
//        test.addLoans(new Loan("test","test",0.12,1000, LocalDate.of(2000,10,15),LocalDate.now()));
//        System.out.println(test);
//    }
}

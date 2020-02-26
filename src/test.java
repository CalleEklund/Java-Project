import classes.User;
import classes.Loan;

import java.time.LocalDate;


public class test
{
    public static void main(String[] args) {
	User u = new User("Calle","test@gmail.com","losen");
	Loan l1 = new Loan("test1", "testdesc1", 1.2, 1000, 100, LocalDate.of(2000,10,15),LocalDate.now());
	Loan l2 = new Loan("test2", "testdesc2", 1.2, 1000, 100, LocalDate.of(2000,10,15),LocalDate.now());
//	u.addLoan(l1);
//	u.addLoan(l2);
	u.addUserLoanshm(l1);
	u.addUserLoanshm(l2);
//	System.out.println(u.getUserLoanshm());
//	System.out.println(u.getUserLoanshm());
	System.out.println(u);
	System.out.println(u.getUserLoanshm().get(l1.getUid()));
	System.out.println(u.getUserLoanshm().get(l2.getUid()));
    }
}

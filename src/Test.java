import javax.swing.*;
import java.awt.*;

public class Test
{
    /**
     * TODO fixa så att det finns en cardswitcher class som behöver följa med i konstruktorn på varje page för att knapen ska
     * kunna ändra jpanel. Kolla exemplet i TestClass
     **/

    JFrame frame = new JFrame();
    JPanel cont = new JPanel();

    CardLayout cl = new CardLayout();
    CardSwitcher switcher = new CardSwitcher(cont, cl);

    LoginPage logInPage;
    CreateAccountPage createAccountPage;
    Main mainPage;
    AddLoanPage addLoanPage;


    public Test() {
	logInPage = new LoginPage(switcher);
	mainPage = new Main(switcher);
	createAccountPage = new CreateAccountPage(switcher);
	addLoanPage = new AddLoanPage(switcher);

	UserController uc = new UserController(logInPage, mainPage);

	cont.setLayout(cl);

	cont.add(logInPage, "logInPage");
	cont.add(createAccountPage, "createAccountPage");
	cont.add(mainPage, "mainPage");
	cont.add(addLoanPage, "addLoanPage");
	cl.show(cont, "logInPage");


	frame.add(cont);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(600, 600);
	frame.setResizable(false);
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	Test t = new Test();
    }
}

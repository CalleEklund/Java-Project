package pages;

import mvc_controllers.UserController;
import classes.CardSwitcher;
import mvc_controllers.LoanController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Huvud appen
 */
public class App
{
    JFrame frame = new JFrame();
    JPanel cont = new JPanel();

    CardLayout cl = new CardLayout();
    CardSwitcher switcher = new CardSwitcher(cont, cl);

    LoginPage logInPage;
    CreateAccountPage createAccountPage;
    MainPage mainPage;
    AddLoanPage addLoanPage;

    /**
     * Lägger till alla sidor samt mvc controllers,
     * huvud appen
     */
    public App() {
	logInPage = new LoginPage(switcher);
	mainPage = new MainPage(switcher);
	createAccountPage = new CreateAccountPage(switcher);
	addLoanPage = new AddLoanPage(switcher);

	new UserController(logInPage, mainPage);
	new LoanController(addLoanPage, mainPage);
	cont.setLayout(cl);

	cont.add(logInPage, "logInPage");
	cont.add(createAccountPage, "createAccountPage");
	cont.add(mainPage, "mainPage");
	cont.add(addLoanPage, "addLoanPage");
	cl.show(cont, "logInPage");


	frame.add(cont);
	//frame.setLocation(1200,100);
	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	frame.setSize(600, 600);
	frame.setResizable(false);
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	new App();


    }
}

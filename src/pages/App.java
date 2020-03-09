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
    final static private int windowSize = 600;

    /**
     * Lägger till alla sidor samt mvc controllers, huvud appen
     */
    public App() {
	final CardLayout cl = new CardLayout();
	final JPanel cont = new JPanel();
	final CardSwitcher switcher = new CardSwitcher(cont, cl);
	final LoginPage logInPage = new LoginPage(switcher);
	final MainPage mainPage = new MainPage(switcher);
	final CreateAccountPage createAccountPage = new CreateAccountPage(switcher);
	final AddLoanPage addLoanPage = new AddLoanPage(switcher);

	new UserController(logInPage, mainPage);
	new LoanController(addLoanPage, mainPage);
	cont.setLayout(cl);

	cont.add(logInPage, "logInPage");
	cont.add(createAccountPage, "createAccountPage");
	cont.add(mainPage, "mainPage");
	cont.add(addLoanPage, "addLoanPage");
	cl.show(cont, "logInPage");


	final JFrame frame = new JFrame();
	frame.add(cont);
	//frame.setLocation(1200,100);
	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	frame.setSize(windowSize, windowSize);
	frame.setResizable(false);
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	final App app = new App();


    }
}

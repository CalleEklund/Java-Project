package pages;

import handlers.LoggerBudget;
import mvc_controllers.AdminController;
import mvc_controllers.UserController;
import classes.CardSwitcher;
import mvc_controllers.LoanController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Huvud appen
 * <p>
 * TODO:
 *	 - Gå igenom denna och checka av, https://www.ida.liu.se/~TDDD78/labs/2020/project/grading.shtml
 *	 - Lägg till bilder i projektrapporten
 */
public class App
{
    final static private int WINDOW_SIZE = 600;

    /**
     * Lägger till alla sidor samt mvc controllers, huvudappen Warning (Result of 'new UserController()','new
     * AdminController()','new LoanController()','new App()' is ignored), eftersom klassen inte används utan bara behöver
     * instansieras
     */
    private App() {
	LoggerBudget logger = new LoggerBudget();

	final CardLayout cl = new CardLayout();
	final JPanel cont = new JPanel();
	final CardSwitcher switcher = new CardSwitcher(cont, cl);
	final LoginPage logInPage = new LoginPage(switcher, logger);
	final MainPage mainPage = new MainPage(switcher, logger);
	final CreateAccountPage createAccountPage = new CreateAccountPage(switcher, logger);
	final AddLoanPage addLoanPage = new AddLoanPage(switcher, logger);
	final AdminPage adminPage = new AdminPage(switcher, logger);

	new UserController(logInPage, mainPage);
	new AdminController(logInPage, adminPage);
	new LoanController(addLoanPage, mainPage);
	cont.setLayout(cl);

	cont.add(logInPage, "logInPage");
	cont.add(createAccountPage, "createAccountPage");
	cont.add(mainPage, "mainPage");
	cont.add(addLoanPage, "addLoanPage");
	cont.add(adminPage, "adminPage");
	cl.show(cont, "logInPage");


	final JFrame frame = new JFrame();
	frame.add(cont);
	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
	frame.setResizable(false);
	frame.setVisible(true);
    }


    public enum Utility
    {
	;

	public static void main(String[] args) {
	    new App();
	}
    }
}

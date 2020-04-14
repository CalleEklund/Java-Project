package pages;

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
 *  - Lägg till mer beksrivande och förklarande docstring ovanför varje klass  (syfte, användning, relation till andra klasser)
 *  - Gå igenom denna och checka av, https://www.ida.liu.se/~TDDD78/labs/2020/project/grading.shtml
 *  - Implementera Loggning funktionallitet
 *  - Titta på att implementera en import samt en export funktion
 */
public class App
{
    final static private int WINDOW_SIZE = 600;

    /**
     * Lägger till alla sidor samt mvc controllers, huvudappen
     * Warning (Result of 'new UserController()','new AdminController()','new LoanController()','new App()' is ignored),
     * eftersom klassen inte används utan bara behöver instansieras
     */
    private App() {

	final CardLayout cl = new CardLayout();
	final JPanel cont = new JPanel();
	final CardSwitcher switcher = new CardSwitcher(cont, cl);
	final LoginPage logInPage = new LoginPage(switcher);
	final MainPage mainPage = new MainPage(switcher);
	final CreateAccountPage createAccountPage = new CreateAccountPage(switcher);
	final AddLoanPage addLoanPage = new AddLoanPage(switcher);
	final AdminPage adminPage = new AdminPage(switcher);

	new UserController(logInPage, mainPage);
	new AdminController(logInPage, adminPage);
	new LoanController(addLoanPage, mainPage);
	cont.setLayout(cl);

	cont.add(logInPage, "logInPage");
	cont.add(createAccountPage, "createAccountPage");
	cont.add(mainPage, "mainPage");
	cont.add(addLoanPage, "addLoanPage");
	cont.add(adminPage,"adminPage");
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

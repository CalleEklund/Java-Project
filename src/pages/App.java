package pages;

import mvc_controllers.UserController;
import classes.CardSwitcher;
import mvc_controllers.LoanController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Huvud appen
 *
 * TODO:
 *  - Implementera en abstrakt klass för controllers och för User iaf man skulle vilja skapa flera olika sorters användare,
 *  - Kolla hur det ser ut att implementera en interface för att kunna notify vilken användare som inloggad just nu
 *
 */
public class App
{
    final static private int WINDOW_SIZE = 600;

    /**
     * Lägger till alla sidor samt mvc controllers, huvudappen
     */
    private App() {
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
	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
	frame.setResizable(false);
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	final App app = new App();


    }
}

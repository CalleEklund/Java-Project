package pages;

import classes.CardSwitcher;
import classes.User;
import classes.UserType;
import classes.Validator;
import handlers.LoggerBudget;
import net.miginfocom.swing.MigLayout;
import handlers.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

/**
 * Den grafiska sidan som ger admin användare tillgång till att logga in som "vanlig" använder eller admin användare.
 */
public class LoginPage extends JPanel implements Page
{

    private final static int TEXT_AREA_COLUMN_SIZE = 20;

    private final static int TITLE_FONT_SIZE = 38;
    private final static int BREAD_FONT_SIZE = 18;

    private final static Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private final static Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);


    private JLabel errorMessagelbl;

    private JButton logInbtn;
    private JButton toCreateAccountPagebtn;

    private JTextField emailInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
    private JPasswordField passwordInput = new JPasswordField(TEXT_AREA_COLUMN_SIZE);

    private Database db;
    private Validator validator = new Validator();

    private LoggerBudget logInLogger;

    /**
     * Konstruktor som skapar den grafiska layouten samt sätter en logger för sidan och en switcher som gör övergången till
     * andra sidor möjligt. Samt skapar en databas koppling för att kunna spara de gjorda ändringar.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param logger   Loggerklassen som används för att logga varning/info för sidan.
     */
    public LoginPage(CardSwitcher switcher, LoggerBudget logger) {
	logInLogger = logger;

	db = new Database(logger);
	setLayout(new MigLayout("fillx"));

	final JLabel titlelbl = new JLabel("*BUDGET*");
	titlelbl.setFont(TITLE_FONT);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	final JLabel emaillbl = new JLabel("Email: ");
	emaillbl.setFont(BREAD_FONT);
	final JLabel passwordlbl = new JLabel("Lösenord: ");
	passwordlbl.setFont(BREAD_FONT);


	add(emaillbl, "alignx center,gap 0 0 80 0");
	add(emailInput, "wrap, h 30");

	add(passwordlbl, "alignx center,gap 0 0 30 0");
	add(passwordInput, "wrap, h 30");

	errorMessagelbl = new JLabel();
	errorMessagelbl.setForeground(Color.RED);
	add(errorMessagelbl, "wrap,alignx center,spanx");

	logInbtn = new JButton("Logga in");
	logInUser(switcher);

	add(logInbtn, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	final JLabel noAccountlbl = new JLabel("har du inte ett konto ?");
	add(noAccountlbl, "wrap,alignx center,spanx");


	toCreateAccountPagebtn = new JButton("Skapa konto");
	createAccountPage(switcher);
	add(toCreateAccountPagebtn, "wrap,alignx center,spanx");

	final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");
	add(copyrightlbl, "spanx,alignx right,gap 0 0 135 0");


    }

    /**
     * Skickar användare till skapa konto sidan.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void createAccountPage(final CardSwitcher switcher) {
	toCreateAccountPagebtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		switchPage(switcher, "createAccountPage");

	    }
	});
    }

    /**
     * Validerar indatan samt loggar in användare till antingen MainPage eller AdminPage beroende på vilke typ av användare som
     * loggas in. Samt ger felmeddelande till användare och loggar de eventuella fel som kan uppstå.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void logInUser(final CardSwitcher switcher) {
	logInbtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		String email = emailInput.getText();
		String password = new String(passwordInput.getPassword());
		if (validator.validateEmptyInput(email) || validator.validateEmptyInput(password)) {
		    errorMessagelbl.setText("Tom indata");
		    logInLogger.logMsg(Level.WARNING, "tom indata vid inloggning");
		    emailInput.setText("");
		    passwordInput.setText("");
		} else if (!validator.validateEmail(email)) {
		    errorMessagelbl.setText("Felaktig email");
		    logInLogger.logMsg(Level.WARNING, "felaktigt email vid inloggning");
		    emailInput.setText("");
		} else {
		    if (db.userExists(email, password)) {
			errorMessagelbl.setText("");
			User u = db.getUser(email, password);
			logInLogger.logMsg(Level.INFO, "Korrekt inloggning med email: " + u.getEmail());
			if (u.getUserType().equals(UserType.ORDINARY)) {
			    switchPage(switcher, "mainPage");
			} else {
			    switchPage(switcher, "adminPage");
			}
		    } else {
			errorMessagelbl.setText("Det finns ingen sådan användare");
			logInLogger.logMsg(Level.WARNING, "Felaktig användare");

		    }
		}
	    }
	});
    }

    /**
     * Sätta den nuvarande inloggade användaren
     *
     * @return get UserController tillgång till den inloggade användaren
     */
    public User getLoggedInUser() {
	String email = emailInput.getText();
	String password = new String(passwordInput.getPassword());
	if (db.userExists(email, password)) {
	    User loggedInUser = db.getUser(email, password);
	    return loggedInUser;
	}
	return new User();

    }

    /**
     * Lägger till en lyssnare för den inloggade användaren.
     *
     * @param listenForLogIn Lyssnare för inloggade användare
     */
    public void addLogInListener(ActionListener listenForLogIn) {
	logInbtn.addActionListener(listenForLogIn);
    }

    /**
     * Interface krav från Page interface som gör det möjligt att byta mellan de olika sidorna.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param newPage  Den nya sidan som övergånge ska gå till.
     */
    @Override public void switchPage(CardSwitcher switcher, String newPage) {
	switcher.switchTo(newPage);
    }
}

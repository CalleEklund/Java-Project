package pages;

import classes.CardSwitcher;
import classes.User;
import classes.UserTypes;
import net.miginfocom.swing.MigLayout;
import savehandlers.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Logga in
 */
public class LoginPage extends JPanel implements Page
{

    final static private int TEXT_AREA_COLUMN_SIZE = 20;


    private JLabel errorMessagelbl;

    private JButton logInbtn;
    private JButton toCreateAccountPagebtn;

    private JTextField emailInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
    private JPasswordField passwordInput = new JPasswordField(TEXT_AREA_COLUMN_SIZE);

    private Database db;

    /**
     * Layout init.
     *
     * @param switcher cardlayout för att kunna byta mellan sidorna
     */
    public LoginPage(CardSwitcher switcher) {
	db = new Database();

	setLayout(new MigLayout("fillx"));

	final int titleFontSize = 38;
	Font titleFont = new Font(Font.SERIF, Font.PLAIN, titleFontSize);
	final JLabel titlelbl = new JLabel("*BUDGET*");
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	final int breadFontSize = 18;
	Font breadFont = new Font(Font.SERIF, Font.PLAIN, breadFontSize);
	final JLabel emaillbl = new JLabel("Email: ");
	emaillbl.setFont(breadFont);
	final JLabel passwordlbl = new JLabel("Lösenord: ");
	passwordlbl.setFont(breadFont);


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

    private void createAccountPage(final CardSwitcher switcher) {
	toCreateAccountPagebtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		switchPage(switcher, "createAccountPage");

	    }
	});
    }

    /**
     * Validerar indatan samt kollar mot textfil "databasen" skickar vidare användare om rätt användare inmatats
     */
    private void logInUser(final CardSwitcher switcher) {
	logInbtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		String email = emailInput.getText();
		String password = new String(passwordInput.getPassword());
		if (!validateInput(email, password)) {
		    emailInput.setText("");
		    passwordInput.setText("");
		} else {
		    if (db.userExists(email, password)) {
			errorMessagelbl.setText("");
			User u = db.getUser(email, password);
			if (u.getUserType().equals(UserTypes.ORDINARY)) {
			    switchPage(switcher, "mainPage");
			} else {
			    switchPage(switcher, "adminPage");
			}
		    } else {
			errorMessagelbl.setText("Det finns ingen sådan användare");
		    }
		}
	    }
	});
    }

    /**
     * Validerar input mot tom sträng samt giltig email, skriver även ut ett felmeddelande om felaktigt input angetts
     *
     * @param email    användarens email
     * @param password användares lösenord
     * @return True/False beroende på om giltig input eller inte
     */
    public boolean validateInput(String email, String password) {
	String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$";
	if (email.isEmpty() || password.isEmpty()) {
	    errorMessagelbl.setText("tom indata");
	    return false;
	} else if (!email.matches(regex)) {
	    errorMessagelbl.setText("felaktig email");
	    return false;
	} else {
	    return true;
	}
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

    public void addLogInListener(ActionListener listenForLogIn) {
	logInbtn.addActionListener(listenForLogIn);
    }

    @Override public void switchPage(CardSwitcher switcher, String newPage) {
	switcher.switchTo(newPage);
    }
}

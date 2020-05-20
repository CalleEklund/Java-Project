package pages;

import classes.CardSwitcher;
import classes.User;
import classes.UserTypes;
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
 * Skapa konto sidan
 */
public class CreateAccountPage extends JPanel implements Page
{

    final static private int TEXT_AREA_COLUMN_SIZE = 20;

    final static int TITLE_FONT_SIZE = 38;
    final static int BREAD_FONT_SIZE = 18;

    final static Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    final static Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private JLabel errorMessagelbl;

    private JButton toLoginPage;
    private JButton createAccount;

    private ButtonGroup userTypeButtons;

    private JTextField nameInput;
    private JTextField emailInput;
    private JPasswordField passwordInput;

    private Database db;
    private Validator validator = new Validator();
    private LoggerBudget createAccountLogger = null;


    /**
     * Layout init.
     *
     * @param switcher cardlayout för att kunna byta mellan sidorna
     */
    public CreateAccountPage(CardSwitcher switcher, LoggerBudget logger) {
	createAccountLogger = logger;
	db = new Database(logger);

	setLayout(new MigLayout("fillx"));

	final JPanel formPanel = new JPanel();
	formPanel.setLayout(new MigLayout("fillx"));


	final JLabel titlelbl = new JLabel("*BUDGET*");
	titlelbl.setFont(TITLE_FONT);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	final JLabel namelbl = new JLabel("Namn: ");
	namelbl.setFont(BREAD_FONT);
	final JLabel emaillbl = new JLabel("Email: ");
	emaillbl.setFont(BREAD_FONT);
	final JLabel passwordlbl = new JLabel("Lösenord: ");
	passwordlbl.setFont(BREAD_FONT);

	add(namelbl, "alignx center,gap 0 0 30 0");
	nameInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(nameInput, "wrap, h 30");

	add(emaillbl, "alignx center,gap 0 0 30 0");
	emailInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(emailInput, "wrap, h 30");

	add(passwordlbl, "alignx center,gap 0 0 30 0");
	passwordInput = new JPasswordField(TEXT_AREA_COLUMN_SIZE);
	add(passwordInput, "wrap, h 30");
	final JPanel buttons = new JPanel();

	final JRadioButton ordinaryUser = new JRadioButton("Vanlig");
	ordinaryUser.setFont(BREAD_FONT);
	ordinaryUser.setSelected(true);
	ordinaryUser.setActionCommand("ordinary");
	final JRadioButton adminUser = new JRadioButton("Admin");
	adminUser.setFont(BREAD_FONT);
	adminUser.setActionCommand("admin");
	userTypeButtons = new ButtonGroup();

	userTypeButtons.add(ordinaryUser);
	userTypeButtons.add(adminUser);

	buttons.add(ordinaryUser);
	buttons.add(adminUser);
	add(buttons, "wrap, spanx, alignx center");

	errorMessagelbl = new JLabel();
	add(errorMessagelbl, "wrap,alignx center,spanx");


	createAccount = new JButton("Skapa konto");

	logOut(switcher);

	add(createAccount, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	final JLabel noAccountlbl = new JLabel("har du redan ett konto ?");
	add(noAccountlbl, "wrap,alignx center,spanx");


	toLoginPage = new JButton("Logga in");
	logInPage(switcher);
	add(toLoginPage, "wrap,alignx center,spanx");

	final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");
	add(copyrightlbl, "spanx,alignx right,gap 0 0 120 0");

    }


    /**
     * Kollar valideringen och sparar användaren
     */
    private void logOut(final CardSwitcher switcher) {
	createAccount.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		String userType = userTypeButtons.getSelection().getActionCommand();
		String name = nameInput.getText();
		String email = emailInput.getText();
		String password = new String(passwordInput.getPassword());
		User newUser;
		if (userType.equals("ordinary")) {
		    newUser = new User(name, email, password, UserTypes.ORDINARY);
		} else {
		    newUser = new User(name, email, password, UserTypes.ADMIN);
		}
		if (validator.validateEmptyInput(name) || validator.validateEmptyInput(email) ||
		    validator.validateEmptyInput(password)) {
		    errorMessagelbl.setText("tom indata");

		} else if (!validator.validateEmail(email)) {
		    errorMessagelbl.setText("felaktig email");
		} else if (!validator.validateIsString(name) || !validator.validateIsString(email) ||
			   !validator.validateIsString(password)) {
		    errorMessagelbl.setText("Ogiltig inmatning (bokstäver ist för siffror)");

		} else {
		    saveUser(newUser);
		    createAccountLogger
			    .logMsg(Level.INFO, "Skapade " + newUser.getUserType() + " med email: " + newUser.getEmail());
		    errorMessagelbl.setText("Konto skapat");
		}
	    }
	});
    }

    private void logInPage(final CardSwitcher switcher) {
	toLoginPage.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		switchPage(switcher, "logInPage");
	    }
	});
    }

    /**
     * Kolla om användare u finns finns i "databasen", om inte spara användaren i textfilen
     *
     * @param u från User klassen
     */
    public void saveUser(User u) {
	String email = u.getEmail();
	String password = u.getPassword();
	if (!db.userExists(email, password)) {
	    db.insertUser(u);
	} else {
	    errorMessagelbl.setForeground(Color.red);
	    errorMessagelbl.setText("Emailen är redan registrerad");
	}
    }

    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

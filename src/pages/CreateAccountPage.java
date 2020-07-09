package pages;

import handlers.CardSwitcher;
import handlers.ProjectLogger;
import user_loan_classes.User;
import user_loan_classes.UserType;
import handlers.Validator;
import net.miginfocom.swing.MigLayout;
import handlers.Database;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

/**
 * Den grafiska sidan som skapar en ny användare samt sparar den till databasen.
 */
public class CreateAccountPage extends JPanel implements Page
{

    private final static int TEXT_AREA_COLUMN_SIZE = 20;

    private final static int TITLE_FONT_SIZE = 38;
    private final static int BREAD_FONT_SIZE = 18;

    private final static Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private final static Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private JLabel errorMessageLabel;

    private JButton toLoginPageButton;
    private JButton createAccountButton;

    private ButtonGroup userTypeButtons;

    private JTextField nameInput;
    private JTextField emailInput;
    private JPasswordField passwordInput;

    private Database db;
    private Validator validator = new Validator();
    private ProjectLogger createAccountProjectLogger;


    /**
     * Konstruktor som skapar den grafiska layouten samt sätter en projectLogger för sidan och en switcher som gör övergången till
     * andra sidor möjligt. Samt skapar en databas koppling för att kunna spara de gjorda ändringar.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param projectLogger   Loggerklassen som används för att logga varning/info för sidan.
     */
    public CreateAccountPage(CardSwitcher switcher, ProjectLogger projectLogger) {
	createAccountProjectLogger = projectLogger;
	db = new Database(projectLogger);

	setLayout(new MigLayout("fillx"));

	final JPanel formPanel = new JPanel();
	formPanel.setLayout(new MigLayout("fillx"));


	final JLabel titleLabel = new JLabel("*BUDGET*");
	titleLabel.setFont(TITLE_FONT);
	add(titleLabel, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	final JLabel nameLabel = new JLabel("Namn: ");
	nameLabel.setFont(BREAD_FONT);
	final JLabel emailLabel = new JLabel("Email: ");
	emailLabel.setFont(BREAD_FONT);
	final JLabel passwordLabel = new JLabel("Lösenord: ");
	passwordLabel.setFont(BREAD_FONT);

	add(nameLabel, "alignx center,gap 0 0 30 0");
	nameInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(nameInput, "wrap, h 30");

	add(emailLabel, "alignx center,gap 0 0 30 0");
	emailInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(emailInput, "wrap, h 30");

	add(passwordLabel, "alignx center,gap 0 0 30 0");
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

	errorMessageLabel = new JLabel();
	add(errorMessageLabel, "wrap,alignx center,spanx");


	createAccountButton = new JButton("Skapa konto");

	toMainPage(switcher);

	add(createAccountButton, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	final JLabel noAccountLabel = new JLabel("har du redan ett konto ?");
	add(noAccountLabel, "wrap,alignx center,spanx");


	toLoginPageButton = new JButton("Logga in");
	logInPage(switcher);
	add(toLoginPageButton, "wrap,alignx center,spanx");

	final JLabel copyrightLabel = new JLabel("Carl Eklund Copyright©");
	add(copyrightLabel, "spanx,alignx right,gap 0 0 120 0");

    }


    /**
     * Validerar användares input samt skickar användaren till inloggningssidan.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void toMainPage(final CardSwitcher switcher) {
	createAccountButton.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		String userType = userTypeButtons.getSelection().getActionCommand();
		String name = nameInput.getText();
		String email = emailInput.getText();
		String password = new String(passwordInput.getPassword());
		User newUser;
		if (userType.equals("ordinary")) {
		    newUser = new User(name, email, password, UserType.ORDINARY);
		} else {
		    newUser = new User(name, email, password, UserType.ADMIN);
		}
		if (validator.validateEmptyInput(name) || validator.validateEmptyInput(email) ||
		    validator.validateEmptyInput(password)) {
		    createAccountProjectLogger.logMsg(Level.WARNING, "Tom indata");
		    errorMessageLabel.setText("Tom indata");

		} else if (!validator.validateEmail(email)) {
		    createAccountProjectLogger.logMsg(Level.WARNING, "Felaktig email");

		    errorMessageLabel.setText("Felaktig email");
		} else if (!validator.validateIsString(name) || !validator.validateIsString(email) ||
			   !validator.validateIsString(password)) {
		    createAccountProjectLogger.logMsg(Level.WARNING, "Ogiltig inmatning (bokstäver ist för siffror)");

		    errorMessageLabel.setText("Ogiltig inmatning (bokstäver ist för siffror)");

		} else {
		    saveUser(newUser);
		    createAccountProjectLogger
			    .logMsg(Level.INFO, "Skapade " + newUser.getUserType() + " med email: " + newUser.getEmail());
		    errorMessageLabel.setText("Konto skapat");
		}
	    }
	});
    }

    /**
     * Används när användare inte vill skapa en ny användare utan bara gå tillbaka till inloggningssidan.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void logInPage(final CardSwitcher switcher) {
	toLoginPageButton.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		switchPage(switcher, "logInPage");
	    }
	});
    }

    /**
     * Kollar om användare finns i database, om inte ge felmeddelande till användaren annars spara användare.
     *
     * @param u från User klassen
     */
    private void saveUser(User u) {
	String email = u.getEmail();
	String password = u.getPassword();
	if (!db.userExists(email, password)) {
	    db.insertUser(u);
	} else {
	    errorMessageLabel.setForeground(Color.red);
	    errorMessageLabel.setText("Emailen är redan registrerad");
	    createAccountProjectLogger.logMsg(Level.WARNING, "Email är redan registrerad");
	}
    }
    /**
     * Interface krav från Page interface som gör det möjligt att byta mellan de olika sidorna.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param newPage Den nya sidan som övergånge ska gå till.
     */
    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

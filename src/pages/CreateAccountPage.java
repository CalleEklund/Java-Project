package pages;

import classes.CardSwitcher;
import classes.User;
import net.miginfocom.swing.MigLayout;
import texthandlers.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Skapa konto sidan
 */
public class CreateAccountPage extends JPanel
{

    final static private int TEXT_AREA_COLUMN_SIZE = 20;

    private JLabel errorMessagelbl;

    private JTextField nameInput;
    private JTextField emailInput;
    private JPasswordField passwordInput;

    private Database db;

    /**
     * Layout init.
     * @param switcher cardlayout för att kunna byta mellan sidorna
     */
    public CreateAccountPage(CardSwitcher switcher) {
	db = new Database();

	setLayout(new MigLayout("fillx"));

	final JPanel formPanel = new JPanel();
	formPanel.setLayout(new MigLayout("fillx"));

	final JLabel titlelbl = new JLabel("*BUDGET*");
	final int titleFontSize = 38;
	final Font titleFont = new Font(Font.SERIF, Font.PLAIN, titleFontSize);
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	final JLabel namelbl = new JLabel("Namn: ");
	final int breadFontSize = 18;
	final Font breadFont = new Font(Font.SERIF, Font.PLAIN, breadFontSize);
	namelbl.setFont(breadFont);
	final JLabel emaillbl = new JLabel("Email: ");
	emaillbl.setFont(breadFont);
	final JLabel passwordlbl = new JLabel("Lösenord: ");
	passwordlbl.setFont(breadFont);


	add(namelbl, "alignx center,gap 0 0 30 0");
	nameInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(nameInput, "wrap, h 30");

	add(emaillbl, "alignx center,gap 0 0 30 0");
	emailInput = new JTextField(TEXT_AREA_COLUMN_SIZE);
	add(emailInput, "wrap, h 30");

	add(passwordlbl, "alignx center,gap 0 0 30 0");
	passwordInput = new JPasswordField(TEXT_AREA_COLUMN_SIZE);
	add(passwordInput, "wrap, h 30");

	errorMessagelbl = new JLabel();
	add(errorMessagelbl, "wrap,alignx center,spanx");

	final JButton createAccount = new JButton("Skapa konto");
	/**
	 * Kollar valideringen och sparanvändare
	 */
	final Action createAcc = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {


		if (!validateInput()) {
		    nameInput.setText("");
		    emailInput.setText("");
		    passwordInput.setText("");
		} else {
		    String name = nameInput.getText();
		    String email = emailInput.getText();
		    String password = new String(passwordInput.getPassword());
		    User newUser = new User(name, email, password);
		    errorMessagelbl.setForeground(Color.GREEN);
		    errorMessagelbl.setText("Konto skapat");
		    saveUserToFile(newUser);

		}
	    }
	};
	createAccount.addActionListener(createAcc);

	add(createAccount, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	final JLabel noAccountlbl = new JLabel("har du redan ett konto ?");
	add(noAccountlbl, "wrap,alignx center,spanx");

	Action changePage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("logInPage");
	    }
	};
	final JButton toLoginPage = new JButton("Logga in");
	toLoginPage.addActionListener(changePage);
	add(toLoginPage, "wrap,alignx center,spanx");

	final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");
	add(copyrightlbl, "spanx,alignx right,gap 0 0 120 0");

    }

    /**
     * Kolla om användare u finns finns i "databasen", om inte spara användaren i textfilen
     * @param u från User klassen
     */
    public void saveUserToFile(User u) {
	if(!db.userExists(u.getEmail())){
	    db.insertUser(u);
	}else{
	    errorMessagelbl.setForeground(Color.red);
	    errorMessagelbl.setText("Emailen är redan registrerad");
	}
    }

    /**
     * Validerar input om giltigt namn,lösenord samt email angetts
     * ger ett felmeddelande till använder som fel input angetts
     * @return True/False beroende på om giltig input eller inte
     */
    public boolean validateInput() {
	errorMessagelbl.setForeground(Color.red);
	String name = nameInput.getText();
	String email = emailInput.getText();
	String password = new String(passwordInput.getPassword());


	try {
	    Integer.parseInt(name);
	    Integer.parseInt(email);
	    Integer.parseInt(password);
	    errorMessagelbl.setText("Ogiltig inmatning (bokstäver ist för siffror)");
	    return false;
	} catch (NumberFormatException e) {
	    System.out.println("Error: " + e);
	    String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$";
	    if (name.length() <= 0 || email.length() <= 0 || password.length() <= 0) {
		errorMessagelbl.setText("tom indata");
		return false;
	    } else if (!email.matches(regex)) {
		errorMessagelbl.setText("felaktig email");
		return false;
	    } else {
		return true;
	    }
	}


    }
}

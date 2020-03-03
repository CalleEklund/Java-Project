package pages;

import classes.CardSwitcher;
import classes.User;
import net.miginfocom.swing.MigLayout;
import texthandlers.TextReader;
import texthandlers.TextWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateAccountPage extends JPanel
{
    /**
     * TODO: - Validering från textfil om användare finns - Implementera filehanterare
     **/
    JPanel formPanel = new JPanel();

    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 22);
    final JLabel titlelbl = new JLabel("*BUDGET*");
    final JLabel namelbl = new JLabel("Namn: ");
    final JLabel emaillbl = new JLabel("Email: ");
    final JLabel passwordlbl = new JLabel("Lösenord: ");
    final JLabel noAccountlbl = new JLabel("har du redan ett konto ?");
    final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");

    final JLabel errorMessagelbl = new JLabel();

    final JButton createAccount = new JButton("Skapa konto");
    final JButton toLoginPage = new JButton("Logga in");

    final JTextField nameInput = new JTextField(20);
    final JTextField emailInput = new JTextField(20);
    final JPasswordField passwordInput = new JPasswordField(20);

    TextWriter tw;
    TextReader tr;

    public CreateAccountPage(CardSwitcher switcher) {
	tw = new TextWriter();
        tr = new TextReader();

	setLayout(new MigLayout("fillx"));

	formPanel.setLayout(new MigLayout("fillx"));

	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	namelbl.setFont(breadFont);
	emaillbl.setFont(breadFont);
	passwordlbl.setFont(breadFont);


	add(namelbl, "alignx center,gap 0 0 30 0");
	add(nameInput, "wrap, h 30");

	add(emaillbl, "alignx center,gap 0 0 30 0");
	add(emailInput, "wrap, h 30");

	add(passwordlbl, "alignx center,gap 0 0 30 0");
	add(passwordInput, "wrap, h 30");

	add(errorMessagelbl, "wrap,alignx center,spanx");

	createAccount.addActionListener(createAcc);

	add(createAccount, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	add(noAccountlbl, "wrap,alignx center,spanx");

	Action changePage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("logInPage");
	    }
	};
	toLoginPage.addActionListener(changePage);
	add(toLoginPage, "wrap,alignx center,spanx");

	add(copyrightlbl, "spanx,alignx right,gap 0 0 120 0");

    }

    Action createAcc = new AbstractAction()
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

    public void saveUserToFile(User u) {
//	System.out.println(tr.checkIfUserExists(u));
	if (!tr.checkIfUserExists(u)) {
	    tw.setCurrentUser(u);
	    tw.writeToFile();
	} else {
	    System.out.println("konto finns redan");
	    errorMessagelbl.setForeground(Color.red);
	    errorMessagelbl.setText("Emailen är redan registrerad");
	}
    }

    public boolean validateInput() {
	String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	errorMessagelbl.setForeground(Color.red);
	String name, email, password;
	name = nameInput.getText();
	email = emailInput.getText();
	password = new String(passwordInput.getPassword());


	try {
	    Integer.parseInt(name);
	    Integer.parseInt(email);
	    Integer.parseInt(password);
	    errorMessagelbl.setText("Ogiltig inmatning (bokstäver ist för siffror)");
	    return false;
	} catch (NumberFormatException e) {
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

    public static void main(String[] args) {
	JFrame frame = new JFrame();

//        frame.add(new pages.CreateAccountPage());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(600, 600);
	frame.setResizable(false);
//	    frame.pack();
	frame.setVisible(true);

    }


}

package pages;

import classes.CardSwitcher;
import classes.User;
import net.miginfocom.swing.MigLayout;
import texthandlers.SaveData;
import texthandlers.TextReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel
{
    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 22);


    final JLabel titlelbl = new JLabel("*BUDGET*");
    final JLabel emaillbl = new JLabel("Email: ");
    final JLabel passwordlbl = new JLabel("Lösenord: ");
    final JLabel noAccountlbl = new JLabel("har du inte ett konto ?");
    final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");
    final JLabel errorMessagelbl = new JLabel();

    final JButton logInbtn = new JButton("Logga in");
    final JButton toCreateAccountPagebtn = new JButton("Skapa konto");

    final JTextField emailInput = new JTextField(20);
    final JPasswordField passwordInput = new JPasswordField(20);

    final TextReader tr = new TextReader();

    final SaveData sd = new SaveData();

    public LoginPage(CardSwitcher switcher) {


	setLayout(new MigLayout("fillx"));

	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,top,alignx center,spanx, gap 0 0 20 20");

	emaillbl.setFont(breadFont);
	passwordlbl.setFont(breadFont);


	add(emaillbl, "alignx center,gap 0 0 80 0");
	add(emailInput, "wrap, h 30");

	add(passwordlbl, "alignx center,gap 0 0 30 0");
	add(passwordInput, "wrap, h 30");

	errorMessagelbl.setForeground(Color.RED);
	add(errorMessagelbl, "wrap,alignx center,spanx");
	//testing only, REMOVE
	emailInput.setText("test@gmail.com");
	passwordInput.setText("testlosen");
	Action logInuser = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		/**
		 * TODO:
		 *  - kolla indata mot en textfil, evt. en databas
		 *  -klar med att kolla om ett konto finns när man ska skapa ett, gör samma sak vid inlogg
		 **/
		String email = emailInput.getText();
		String password = new String(passwordInput.getPassword());
		User newUser = new User(email, password);
		if (!validateInput(newUser)) {
		    emailInput.setText("");
		    passwordInput.setText("");
		} else {
		    System.out.println("exits"+sd.checkIfUserExists(newUser));
		    if (sd.checkIfUserExists(newUser)) {
			switcher.switchTo("mainPage");
		    } else {
			errorMessagelbl.setText("Det finns ingen sådan användare");
		    }
		}

	    }
	};

	logInbtn.addActionListener(logInuser);

	add(logInbtn, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
	add(noAccountlbl, "wrap,alignx center,spanx");


	Action changePage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("createAccountPage");
	    }
	};

	toCreateAccountPagebtn.addActionListener(changePage);
	add(toCreateAccountPagebtn, "wrap,alignx center,spanx");

	add(copyrightlbl, "spanx,alignx right,gap 0 0 135 0");


    }

    public boolean validateInput(User user) {
	String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	if (user.getEmail().length() <= 0 || user.getPassword().length() <= 0) {
	    errorMessagelbl.setText("tom indata");
	    return false;
	} else if (!user.getEmail().matches(regex)) {
	    errorMessagelbl.setText("felaktig email");
	    return false;
	} else {
	    return true;
	}
    }

    public User getLoggedInUser() {
	String email = emailInput.getText();
	String password = new String(passwordInput.getPassword());
	return sd.getUser(email,password);
    }

    public void addLogInListener(ActionListener listenForLogIn) {
	logInbtn.addActionListener(listenForLogIn);
    }
}

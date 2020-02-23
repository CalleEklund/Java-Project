import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {
    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 22);

    final JLabel titlelbl = new JLabel("*BUDGET*");
    final JLabel emaillbl = new JLabel("Email: ");
    final JLabel passwordlbl = new JLabel("Lösenord: ");
    final JLabel noAccountlbl = new JLabel("har du inte ett konto ?");
    final JLabel copyrightlbl = new JLabel("Carl Eklund Copyright©");

    final JButton logInbtn = new JButton("Logga in");
    final JButton toCreateAccountPagebtn = new JButton("Skapa konto");

    final JTextField emailInput = new JTextField(20);
    final JPasswordField passwordInput = new JPasswordField(20);


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

        Action logInuser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /**
                 * TODO:
                 *  - Gör en validering på indatan,
                 *  - kolla indata mot en textfil, evt. en databas
                 * **/
                switcher.switchTo("mainPage");
            }
        };

        logInbtn.addActionListener(logInuser);

        add(logInbtn, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
        add(noAccountlbl, "wrap,alignx center,spanx");


        Action changePage = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switcher.switchTo("createAccountPage");
            }
        };

        toCreateAccountPagebtn.addActionListener(changePage);
        add(toCreateAccountPagebtn, "wrap,alignx center,spanx");

        add(copyrightlbl, "spanx,alignx right,gap 0 0 135 0");


    }

    public User getLoggedInUser() {
        String email = emailInput.getText();
        String password = new String(passwordInput.getPassword());
        User currentUser = new User(email, password);
        return currentUser;
    }

    void addLogInListener(ActionListener listenForLogIn) {
        logInbtn.addActionListener(listenForLogIn);
    }
}

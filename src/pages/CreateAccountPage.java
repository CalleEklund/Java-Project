package pages;

import classes.CardSwitcher;
import classes.User;
import net.miginfocom.swing.MigLayout;
import texthandlers.TextWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateAccountPage extends JPanel {
    /**
     * TODO:
     *  - Validering från textfil om användare finns
     *  - Implementera filehanterare
     *
     * **/
    JPanel formPanel = new JPanel();

    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 22);
    final JLabel title = new JLabel("*BUDGET*");
    final JLabel name = new JLabel("Namn: ");
    final JLabel email = new JLabel("Email: ");
    final JLabel password = new JLabel("Lösenord: ");
    final JLabel noAccount = new JLabel("har du redan ett konto ?");
    final JLabel copyright = new JLabel("Carl Eklund Copyright©");

    final JLabel errorMessage = new JLabel();

    final JButton createAccount = new JButton("Skapa konto");
    final JButton toLoginPage = new JButton("Logga in");

    final JTextField nameInput = new JTextField(20);
    final JTextField emailInput = new JTextField(20);
    final JPasswordField  passwordInput = new JPasswordField (20);


    public CreateAccountPage(CardSwitcher switcher) {
        TextWriter jw = new TextWriter();

        setLayout(new MigLayout("fillx"));

        formPanel.setLayout(new MigLayout("fillx"));

        title.setFont(titleFont);
        add(title, "wrap,top,alignx center,spanx, gap 0 0 20 20");

        name.setFont(breadFont);
        email.setFont(breadFont);
        password.setFont(breadFont);


        add(name, "alignx center,gap 0 0 30 0");
        add(nameInput, "wrap, h 30");

        add(email, "alignx center,gap 0 0 30 0");
        add(emailInput, "wrap, h 30");

        add(password, "alignx center,gap 0 0 30 0");
        add(passwordInput, "wrap, h 30");

        errorMessage.setForeground(Color.RED);
        add(errorMessage,"wrap,alignx center,spanx");

        createAccount.addActionListener(createAcc);

        add(createAccount, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
        add(noAccount, "wrap,alignx center,spanx");

        Action changePage = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switcher.switchTo("logInPage");
            }
        };
        toLoginPage.addActionListener(changePage);
        add(toLoginPage, "wrap,alignx center,spanx");

        add(copyright, "spanx,alignx right,gap 0 0 120 0");

    }

    final Action createAcc = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String name = nameInput.getText();
            String email = emailInput.getText();
            String password = new String(passwordInput.getPassword());

            User newUser = new User(name, email, password);

            if(!validateInput(newUser)){
                nameInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
            }else{
                System.out.println("konto skapat");
            }
        }
    };

    public boolean validateInput(User user) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(user.getName().length() <= 0 || user.getPassword().length() <= 0){
           errorMessage.setText("tom indata");
           return false;
        }else if(!user.getEmail().matches(regex)){
            errorMessage.setText("felaktig email");
            return false;
        }else{
            return true;
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

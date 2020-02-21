import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class LoginPage extends JPanel implements EventListener
{
    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 22);

    final JLabel title = new JLabel("*BUDGET*");
    final JLabel email = new JLabel("Email: ");
    final JLabel password = new JLabel("Lösenord: ");
    final JLabel noAccount = new JLabel("har du inte ett konto ?");
    final JLabel copyright = new JLabel("Carl Eklund Copyright©");


    final JButton logIn = new JButton("Logga in");
    final JButton toCreateAccountPage = new JButton("Skapa konto");

    final JTextField emailInput = new JTextField(20);
    final JTextField passwordInput = new JTextField(20);

    public LoginPage() {

        setLayout(new MigLayout("fillx"));

        title.setFont(titleFont);
        add(title, "wrap,top,alignx center,spanx, gap 0 0 20 20");

        email.setFont(breadFont);
        password.setFont(breadFont);


        add(email, "alignx center,gap 0 0 80 0");
        add(emailInput, "wrap, h 30");

        add(password, "alignx center,gap 0 0 30 0");
        add(passwordInput, "wrap, h 30");


        logIn.addActionListener(test);
        add(logIn, "wrap,alignx center,spanx,height 40,width 200,gap 0 0 50 0");
        add(noAccount, "wrap,alignx center,spanx");

        add(toCreateAccountPage, "wrap,alignx center,spanx");

        add(copyright,"spanx,alignx right,gap 0 0 120 0");

    }
    final Action test = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("test");
        }
    };
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.add(new LoginPage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}

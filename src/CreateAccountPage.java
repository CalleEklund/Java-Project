import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreateAccountPage extends JPanel
{
    JPanel formPanel = new JPanel();

    Font titleFont = new Font(Font.SERIF,Font.PLAIN,38);
    Font breadFont = new Font(Font.SERIF,Font.PLAIN,22);
    final JLabel title = new JLabel("*BUDGET*");
    final JLabel name = new JLabel("Namn: ");
    final JLabel email = new JLabel("Email: ");
    final JLabel password = new JLabel("Lösenord: ");
    final JLabel noAccount = new JLabel("har du redan ett konto ?");

    final JButton createAccount = new JButton("Skapa konto");
    final JButton toLoginPage = new JButton("Logga in");

    final JTextField nameInput = new JTextField(20);
    final JTextField emailInput = new JTextField(20);
    final JTextField passwordInput = new JTextField(20);

    public CreateAccountPage()  {
        setLayout(new MigLayout("fillx,debug"));

        formPanel.setLayout(new MigLayout("fillx"));

	title.setFont(titleFont);
	add(title,"wrap,top,alignx center,spanx, gap 0 0 20 20");

        name.setFont(breadFont);
        email.setFont(breadFont);
        password.setFont(breadFont);



        add(name,"alignx center,gap 0 0 30 0");
        add(nameInput,"wrap, h 30");

        add(email,"alignx center,gap 0 0 30 0");
        add(emailInput,"wrap, h 30");

	add(password,"alignx center,gap 0 0 30 0");
        add(passwordInput,"wrap, h 30");






	add(createAccount,"wrap,alignx center,spanx,height 50,width 100");
        add(noAccount,"wrap");
//
//	add(toLoginPage,"wrap");


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

	frame.add(new CreateAccountPage());
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setSize(600,600);
	frame.setResizable(false);
//	frame.pack();
	frame.setVisible(true);

    }


}

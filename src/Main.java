import javax.swing.*;
import java.awt.*;

public class Main {
    /**
     * TODO
     * fixa så att det finns en cardswitcher class som behöver följa med i konstruktorn
     * på varje page för att knapen ska kunna ändra jpanel.
     * Kolla exemplet i TestClass
     **/



    JFrame frame = new JFrame();
    CardLayout cl = new CardLayout();

    JPanel cont = new JPanel();

    CardSwitcher switcher = new CardSwitcher(cont, cl);

    JPanel logInPage = new LoginPage(switcher);
    JPanel createAccountPage = new CreateAccountPage(switcher);




    public Main() {

        cont.setLayout(cl);

        cont.add(logInPage, "logInPage");
        cont.add(createAccountPage, "createAccountPage");
        cl.show(cont, "logInPage");

        frame.add(cont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

import javax.swing.*;
import java.awt.*;

public class Main
{/**TODO
    fixa så att det finns en cardswitcher class som behöver följa med i konstruktorn
    på varje page för att knapen ska kunna ändra jpanel.
    Kolla exemplet i TestClass

 **/
    JFrame frame = new JFrame();
    JPanel cont =  new JPanel();
    JPanel test1 = new LoginPage();
    JPanel test2 = new CreateAccountPage();
    CardLayout cl = new CardLayout();



    public Main() {
        cont.setLayout(cl);

        cont.add(test1,"1");
        cont.add(test2,"2");
//        System.out.println(test1.);
        cl.show(cont,"1");

        frame.add(cont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
//	frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

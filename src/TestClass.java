import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestClass extends JPanel
{
/**
 * To be used to test gui classes
 *
 * **/
public static void main(String[] args) {
    JFrame frame = new JFrame("Test");

    TestClass tst = new TestClass();
    frame.getContentPane().add(tst);

    frame.setSize(600, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);

}

    public TestClass() {
        setLayout(new BorderLayout());
        CardLayout cardLayout = new CardLayout();

        JPanel centerPanel = new JPanel(cardLayout);

        JPanel panelA = new JPanel();
//        panelA.add(new LoginPage());
        centerPanel.add(panelA, "A");
        JPanel panelB = new JPanel();
//        panelB.add(new CreateAccountPage());
        centerPanel.add(panelB, "B");
        JPanel panelC = new JPanel();
        panelC.add(new AddLoanPage());
        centerPanel.add(panelC, "C");

        CardSwitcher switcher = new CardSwitcher(centerPanel, cardLayout);
        ButtonPanel buttons = new ButtonPanel(switcher);

        add(centerPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);


    }

    class ButtonPanel extends JPanel {
        public ButtonPanel(CardSwitcher switcher) {
            setLayout(new FlowLayout());

            JButton buttonA = new JButton("Show A");
            buttonA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switcher.switchTo("A");
                }
            });
            add(buttonA);

            JButton buttonB = new JButton("Show B");
            buttonB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switcher.switchTo("B");
                }
            });
            add(buttonB);

            JButton buttonC = new JButton("Show C");
            buttonC.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switcher.switchTo("C");
                }
            });
            add(buttonC);

        }
    }


    class CardSwitcher {
        CardLayout layout;
        Container container;

        public CardSwitcher(Container container, CardLayout layout) {
            this.layout = layout;
            this.container = container;
        }

        public void switchTo(String card) {
            layout.show(container, card);
        }
    }
}

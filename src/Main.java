import javax.swing.*;
import java.awt.event.ActionEvent;


public class Main extends JPanel {
    /**
     * TODO:
     *  - Lägg til en logga ut knapp, som tar tillbaka till logInPage
     * **/
    JButton addLoanTest = new JButton("lägg till lån");
    JLabel displayUserTest = new JLabel("User: \n");

    public Main(CardSwitcher switcher) {
        Action logInUser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switcher.switchTo("addLoanPage");
            }
        };

        addLoanTest.addActionListener(logInUser);
        add(addLoanTest);
        add(displayUserTest);
    }
    public void setCurrentUser(User u){
        displayUserTest.setText(u.toString());
    }

}

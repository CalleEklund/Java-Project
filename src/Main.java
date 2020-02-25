import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JPanel {
    /**
     * TODO:
     *  - Lägg til en logga ut knapp, som tar tillbaka till logInPage
     * **/
    JButton addLoanTest = new JButton("lägg till lån");
    JLabel displayUserTest = new JLabel("User: \n");
    User currentUser = null;
    public Main(CardSwitcher switcher) {
        Action addLoan = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //displayCurrentUser(loggedInUser);
                switcher.switchTo("addLoanPage");
            }
        };

        addLoanTest.addActionListener(addLoan);
        add(addLoanTest);
        add(displayUserTest);
    }
    public void displayCurrentUser(User loggedInUser){
        System.out.println(currentUser.toString());
        displayUserTest.setText("loans"+loggedInUser.getUserLoans());
    }
    public void setCurrentUser(User loggedInUser){
        currentUser = loggedInUser;
    }

    public void addLoanToUser(final Loan currentLoan) {
        currentUser.addLoan(currentLoan);
        displayCurrentUser(currentUser);
    }
}

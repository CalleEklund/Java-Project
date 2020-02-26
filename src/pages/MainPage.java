package pages;

import classes.CardSwitcher;
import classes.Loan;
import classes.User;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MainPage extends JPanel {
    /**
     * TODO:
     *  - Lägg til en logga ut knapp, som tar tillbaka till logInPage
     * **/
    JButton addLoanTest = new JButton("lägg till lån");
    JLabel displayUserTest = new JLabel("classes.User: \n");
    User currentUser = null;
    public MainPage(CardSwitcher switcher) {
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
        System.out.println(currentUser.getUserLoanshm().toString());
        displayUserTest.setText("loans"+loggedInUser.getUserLoanshm());
    }
    public void setCurrentUser(User loggedInUser){
        currentUser = loggedInUser;
    }

    public void addLoanToUser(final Loan currentLoan) {
        currentUser.addUserLoanshm(currentLoan);
//        System.out.println(currentLoan);
        displayCurrentUser(currentUser);
    }
}

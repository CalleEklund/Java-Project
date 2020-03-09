package mvc_controllers;

import classes.User;
import pages.LoginPage;
import pages.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * En klass för att kunna temporärt lagra en användare mellan de olika sidorna
 */
public class UserController {
    private LoginPage theModel;
    private MainPage theView;

    public UserController(LoginPage theModel, MainPage theView) {
        this.theModel = theModel;
        this.theView = theView;

        this.theModel.addLogInListener(new LogInListener());
    }

    class LogInListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            User currentUser = theModel.getLoggedInUser();
            theView.setCurrentUser(currentUser);
        }
    }
}

package mvc_controllers;

import classes.User;
import classes.UserTypes;
import pages.LoginPage;
import pages.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * En klass för att kunna temporärt lagra en användare mellan de olika sidorna
 */
public class UserController extends AbstractController
{

    public UserController(LoginPage theModel, MainPage theView) {
	super(theView, theModel);

	theModel.addLogInListener(new UserController.LogInListener());
    }


    private class LogInListener implements ActionListener
    {
	@Override public void actionPerformed(ActionEvent actionEvent) {
	    updateView();
	}


    }

    @Override public void updateView() {
	User currentUser = theModelLogin.getLoggedInUser();
	if (currentUser.getUserType().equals(UserTypes.ORDINARY)) {
	    theViewUser.setCurrentUser(currentUser);
	}

    }
}

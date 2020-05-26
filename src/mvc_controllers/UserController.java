package mvc_controllers;

import classes.User;
import classes.UserType;
import pages.LoginPage;
import pages.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Userkontroller som sköter vilken "vanlig" användare som är inloggad.
 */
public class UserController extends AbstractController
{

    public UserController(LoginPage theModel, MainPage theView) {
	super(theView, theModel);

	theModel.addLogInListener(new UserController.LogInListener());
    }

    /**
     * En lyssnare som kallas på när en användare loggar in.
     */
    private class LogInListener implements ActionListener
    {
	@Override public void actionPerformed(ActionEvent actionEvent) {
	    updateView();
	}


    }

    /**
     * Uppdater view med vilken "vanlig" använder som är inloggad med den datan som skickas från modellen
     */
    @Override public void updateView() {
	User currentUser = modelLogin.getLoggedInUser();
	if (currentUser.getUserType().equals(UserType.ORDINARY)) {
	    viewUser.setCurrentUser(currentUser);
	}

    }
}

package mvc_controllers;

import classes.User;
import pages.AdminPage;
import pages.LoginPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController extends AbstractController
{
    public AdminController(LoginPage theModel, AdminPage theView) {
	super(theView,theModel);

	theModel.addLogInListener(new AdminController.LogInListener());
    }

    private class LogInListener implements ActionListener
    {
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
	    User currentUser = theModelLogin.getLoggedInUser();
	    theViewAdmin.setCurrentAdmin(currentUser);
	}
    }
}

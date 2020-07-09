package mvc_controllers;

import user_loan_classes.User;
import user_loan_classes.UserType;
import pages.AdminPage;
import pages.LoginPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adminkontroller som sköter vilken adminanvändare som är inloggad.
 */

public class AdminController extends AbstractController
{
    public AdminController(LoginPage theModel, AdminPage theView) {
	super(theView, theModel);

	theModel.addLogInListener(new AdminController.LogInListener());
    }

    /**
     * Uppdater view med vilken admin använder som är inloggad med den datan som skickas från modellen
     */
    @Override public void updateView() {
	User currentUser = modelLogin.getLoggedInUser();
	if (currentUser.getUserType().equals(UserType.ADMIN)) {
	    viewAdmin.setCurrentAdmin(currentUser);
	}

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


}

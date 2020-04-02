package mvc_controllers;

import classes.Loan;
import pages.AddLoanPage;
import pages.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * En klass för att kunna temporärt lagra ett lån mellan de olika sidorna
 */
public class LoanController extends AbstractController
{

    public LoanController(final AddLoanPage theModel, final MainPage theView) {
	super(theView, theModel);
	theModel.addAddLoanListener(new AddLoanListener());
    }

    private class AddLoanListener implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    updateView();
	}
    }

    @Override public void updateView() {
	if (theModelLoan.getCurrentLoan() != null) {
	    Loan currentLoan = theModelLoan.getCurrentLoan();
	    theViewUser.addLoanToUser(currentLoan);
	}
    }
}

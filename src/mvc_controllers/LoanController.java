package mvc_controllers;

import classes.Loan;
import pages.AddLoanPage;
import pages.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Loankontroller som sköter vilken lån som skapas för tillfället.
 */
public class LoanController extends AbstractController
{

    public LoanController(final AddLoanPage theModel, final MainPage theView) {
	super(theView, theModel);
	theModel.addAddLoanListener(new AddLoanListener());
    }

    /**
     * En lyssnare som kallas på när ett nytt lån skapas.
     */
    private class AddLoanListener implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    updateView();
	}
    }

    /**
     * Om det finns ett skapat lån så läggs lånet till den inloggade användaren.
     */
    @Override public void updateView() {
	if (modelLoan.getCurrentLoan() != null) {
	    Loan currentLoan = modelLoan.getCurrentLoan();
	    viewUser.addLoanToUser(currentLoan);
	}
    }
}

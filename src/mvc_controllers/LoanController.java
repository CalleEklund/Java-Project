package mvc_controllers;

import classes.Loan;
import pages.AddLoanPage;
import pages.MainPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanController
{
    private AddLoanPage theModel;
    private MainPage theView;

    public LoanController(final AddLoanPage theModel, final MainPage theView) {
	this.theModel = theModel;
	this.theView = theView;
	this.theModel.addAddLoanListener(new AddLoanListener());
    }
    class AddLoanListener implements ActionListener{

	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    Loan currentLoan = theModel.getCurrentLoan();
	    theView.addLoanToUser(currentLoan);
	}
    }
}

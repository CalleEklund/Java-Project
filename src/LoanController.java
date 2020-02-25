import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanController
{
    private AddLoanPage theModel;
    private Main theView;

    public LoanController(final AddLoanPage theModel, final Main theView) {
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

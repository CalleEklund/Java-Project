package mvc_controllers;

import pages.AddLoanPage;
import pages.LoginPage;
import pages.MainPage;


public abstract class AbstractController implements Controller
{
    protected MainPage theView;
    protected AddLoanPage theModelLoan = null;
    protected LoginPage theModelLogin = null;

    public AbstractController(final MainPage theView, final LoginPage theModel) {
	this.theView = theView;
	this.theModelLogin = theModel;
    }
    public AbstractController(final MainPage theView, final AddLoanPage theModel) {
	this.theView = theView;
	this.theModelLoan = theModel;
    }

    @Override public MainPage getTheView() {
	return theView;
    }

    @Override public AddLoanPage getTheModelLoan() {
	return theModelLoan;
    }

    @Override public LoginPage getTheModelLogin() {
	return theModelLogin;
    }

}

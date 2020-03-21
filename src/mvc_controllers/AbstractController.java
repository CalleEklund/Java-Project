package mvc_controllers;

import classes.CardSwitcher;
import pages.AddLoanPage;
import pages.LoginPage;
import pages.MainPage;


public abstract class AbstractController
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

    public MainPage getTheView() {
	return theView;
    }

    public AddLoanPage getTheModelLoan() {
	return theModelLoan;
    }

    public LoginPage getTheModelLogin() {
	return theModelLogin;
    }
}

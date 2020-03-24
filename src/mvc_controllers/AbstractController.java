package mvc_controllers;

import pages.AddLoanPage;
import pages.AdminPage;
import pages.LoginPage;
import pages.MainPage;


public abstract class AbstractController implements Controller
{
    protected MainPage theViewUser = null;
    protected AdminPage theViewAdmin = null;
    protected AddLoanPage theModelLoan = null;
    protected LoginPage theModelLogin = null;

    protected AbstractController(final MainPage theView, final LoginPage theModel) {
	this.theViewUser = theView;
	this.theModelLogin = theModel;
    }

    protected AbstractController(final MainPage theView, final AddLoanPage theModel) {
	this.theViewUser = theView;
	this.theModelLoan = theModel;
    }

    protected AbstractController(final AdminPage theView, final LoginPage theModel) {
	this.theViewAdmin = theView;
	this.theModelLogin = theModel;
    }


}

package mvc_controllers;

import pages.AddLoanPage;
import pages.AdminPage;
import pages.LoginPage;
import pages.MainPage;

/**
 * Abstrakt klass för MVC kontroller som sköter information överföringen mellan de olika grafiska sidorna. En varsin konstruktor
 * för varje kontroller eftersom de hanteras olika.
 */
public abstract class AbstractController implements Controller
{
    protected MainPage viewUser = null;
    protected AdminPage viewAdmin = null;
    protected AddLoanPage modelLoan = null;
    protected LoginPage modelLogin = null;

    protected AbstractController(final MainPage theView, final LoginPage theModel) {
	this.viewUser = theView;
	this.modelLogin = theModel;
    }

    protected AbstractController(final MainPage theView, final AddLoanPage theModel) {
	this.viewUser = theView;
	this.modelLoan = theModel;
    }

    protected AbstractController(final AdminPage theView, final LoginPage theModel) {
	this.viewAdmin = theView;
	this.modelLogin = theModel;
    }


}

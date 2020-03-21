package mvc_controllers;

import pages.AddLoanPage;
import pages.LoginPage;
import pages.MainPage;

public interface Controller
{
    MainPage getTheView();

    AddLoanPage getTheModelLoan();

    LoginPage getTheModelLogin();
}

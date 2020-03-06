package pages;

import classes.CardSwitcher;
import classes.Loan;
import classes.User;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import texthandlers.SaveData;
import misc.TextWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Properties;


public class MainPage extends JPanel
{
    static Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    static Font loantitleFont = new Font(Font.SERIF, Font.PLAIN, 30);
    static Font breadFont = new Font(Font.SERIF, Font.PLAIN, 18);

    final JButton addNewLoan = new JButton("lägg till lån");
    final JLabel titlelbl = new JLabel("*BUDGET*");
    final JButton logOutbtn = new JButton("Logga ut");
    static JTabbedPane loanPanes = new JTabbedPane(JTabbedPane.TOP);

    final SaveData sd = new SaveData();
    User currentUser = null;


    public MainPage(CardSwitcher switcher) {
	setLayout(new MigLayout("fillx,debug"));
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,alignx center,spanx,gap 0 0 20 20");
	add(addNewLoan, "");
	add(logOutbtn, "alignx right,wrap");
	Action addLoan = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("addLoanPage");
	    }
	};

	addNewLoan.addActionListener(addLoan);
	Action logoutUser = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("logInPage");
	    }
	};
	logOutbtn.addActionListener(logoutUser);
	add(loanPanes, "grow,pushy,spanx");


    }
    private static JTabbedPane makePages(ArrayList<Loan> userLoans) {
	JPanel loanPanel = new JPanel();
	int index = 0;
	if (!userLoans.isEmpty()) {
	    loanPanes.removeAll();
	    for (int i = 0; i < userLoans.size(); i++) {
		loanPanel = makeLoanPanel(userLoans.get(i));
		Loan currentLoan = userLoans.get(i);
		loanPanes.insertTab(currentLoan.getTitle(), null, loanPanel, null, index);
		index++;
	    }
	} else {

	    loanPanes.removeAll();
	    loanPanes.insertTab("Inga lån", null, loanPanel, null, 0);
	}
	return loanPanes;
    }

    private static JPanel makeLoanPanel(Loan l) {
	JPanel p = new JPanel();
	p.setLayout(new MigLayout("fillx"));

	UtilDateModel modelStart = new UtilDateModel();
	JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, new Properties());
	UtilDateModel modelEnd = new UtilDateModel();
	JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, new Properties());

	final JDatePickerImpl loanstartDate = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
	final JDatePickerImpl loanendDate = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());


	loanstartDate.getModel().setSelected(true);
	loanstartDate.getModel().setYear(l.getStartDate().getYear());
	loanstartDate.getModel().setMonth(l.getStartDate().getMonthValue());
	loanstartDate.getModel().setDay(l.getStartDate().getDayOfMonth());
	loanstartDate.getComponent(1).setEnabled(false);

	loanendDate.getModel().setSelected(true);
	loanendDate.getModel().setYear(l.getEndDate().getYear());
	loanendDate.getModel().setMonth(l.getEndDate().getMonthValue());
	loanendDate.getModel().setDay(l.getEndDate().getDayOfMonth());
	loanendDate.getComponent(1).setEnabled(false);


	JLabel title = new JLabel(l.getTitle());
	JLabel amount = new JLabel("Mängd kvar:");
	JLabel intrest = new JLabel("Ränta");
	JLabel amortization = new JLabel("Insats per månad");
	JLabel description = new JLabel("Beskrivning");

	JTextField amounttf = new JTextField(String.valueOf(l.getAmount()));
	JTextField intresttf = new JTextField(String.valueOf(l.getIntrest()));
	JTextField amortizationtf = new JTextField(String.valueOf(l.getAmortization()));
	JTextArea descriptionta = new JTextArea(l.getDescription(), 4, 20);

	amounttf.setEditable(false);
	intresttf.setEditable(false);
	amortizationtf.setEditable(false);
	descriptionta.setEditable(false);

	title.setFont(loantitleFont);
	p.add(title, "wrap,alignx left,w 30");

	p.add(loanstartDate);
	p.add(loanendDate, "wrap");

	amount.setFont(breadFont);
	amounttf.setFont(breadFont);
	p.add(amount, "alignx center");
	p.add(amounttf, "wrap");

	amount.setFont(breadFont);
	amounttf.setFont(breadFont);
	p.add(amount, "alignx center");
	p.add(amounttf, "wrap");

	intrest.setFont(breadFont);
	intresttf.setFont(breadFont);
	p.add(intrest, "alignx center");
	p.add(intresttf, "wrap");

	amortization.setFont(breadFont);
	amortizationtf.setFont(breadFont);
	p.add(amortization, "alignx center");
	p.add(amortizationtf, "wrap");

	description.setFont(breadFont);
	descriptionta.setFont(breadFont);
	descriptionta.setLineWrap(true);
	descriptionta.setWrapStyleWord(true);
	p.add(description, "alignx center");
	p.add(descriptionta, "wrap,spanx");


	return p;
    }

    public void setCurrentUser(User loggedInUser) {
	currentUser = loggedInUser;
	System.out.println("current user " + currentUser);
	System.out.println("current userloans " + currentUser.getUserLoans());

	makePages(currentUser.getUserLoans());
    }
    public void addLoanToUser(final Loan currentLoan) {
	sd.saveLoan(currentUser,currentLoan);
	currentUser = sd.getUser(currentUser.getEmail(),currentUser.getPassword());
	makePages(currentUser.getUserLoans());
    }
}

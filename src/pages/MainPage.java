package pages;

import classes.CardSwitcher;
import classes.Loan;
import classes.User;
import handlers.Database;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import static javax.swing.SwingConstants.TOP;

/**
 * Huvudsidan för användaren och dess lån
 */
public class MainPage extends JPanel implements Page
{
    final static private int TITLE_FONT_SIZE = 38;
    final static private int LOAN_TITLE_FONT_SIZE = 30;
    final static private int BREAD_FONT_SIZE = 18;
    final static private int TEXT_AREA_COLUMN_SIZE = 20;


    private static Font titleFont = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private static Font loantitleFont = new Font(Font.SERIF, Font.PLAIN, LOAN_TITLE_FONT_SIZE);
    private static Font breadFont = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private final JButton addNewLoan = new JButton("lägg till lån");
    private final JLabel titlelbl = new JLabel("*BUDGET*");
    private final JButton logOutbtn = new JButton("Logga ut");
    private static JTabbedPane loanPanes = new JTabbedPane(TOP);

    private final Database db;
    private User currentUser = null;

    /**
     * Layout init.
     *
     * @param switcher cardlayout för att kunna byta mellan sidorna
     */
    public MainPage(CardSwitcher switcher) {
	db = new Database();
	setLayout(new MigLayout("fillx"));
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,alignx center,spanx,gap 0 0 20 20");
	add(addNewLoan, "");
	add(logOutbtn, "alignx right,wrap");

	addLoanPage(switcher);
	logOut(switcher);

	add(loanPanes, "grow,pushy,spanx");


    }


    private void logOut(final CardSwitcher switcher) {
	logOutbtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		currentUser = new User();
		switchPage(switcher, "logInPage");
	    }
	});
    }
    private void addLoanPage(final CardSwitcher switcher){
        addNewLoan.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		switchPage(switcher, "addLoanPage");

	    }
	});
    }
    /**
     * Gör Jtabbedpanes för varje lån som finns för användaren
     *
     * @param userLoans inloggade användares lån
     */
    private void makePages(ArrayList<Loan> userLoans) {
	JPanel loanPanel = new JPanel();
	if (!userLoans.isEmpty()) {
	    loanPanes.removeAll();
	    int index = 0;
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
    }

    /**
     * Gör GUI för lånet
     *
     * @param l Lån av klassen classes.Loan
     * @return returnerar GUI:n för det lånet
     */
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
	JLabel amount = new JLabel("Lånebelopp: ");
	JLabel intrest = new JLabel("Ränta: ");
	JLabel amortization = new JLabel("Amortering: ");
	JLabel description = new JLabel("Beskrivning: ");

	JTextField amounttf = new JTextField(String.valueOf(l.getAmount()));
	JTextField intresttf = new JTextField(String.valueOf(l.getIntrest()));
	JTextField amortizationtf = new JTextField(String.valueOf(l.getAmortization()));
	JTextArea descriptionta = new JTextArea(l.getDescription(), 4, TEXT_AREA_COLUMN_SIZE);

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

    /**
     * Sätta den inloggade användare till currentUser
     *
     * @param loggedInUser användare som är inloggad
     */
    public void setCurrentUser(User loggedInUser) {
	String email = loggedInUser.getEmail();
	String password = loggedInUser.getPassword();

	if (db.userExists(email, password)) {
	    currentUser = db.getUser(email, password);
	} else {
	    currentUser = loggedInUser;
	}

	makePages(currentUser.getUserLoans());
    }

    /**
     * Lägger till de nyligen skapta lånet till användaren samt gör en ny panel för de lånet
     *
     * @param currentLoan De senaste skapta lånet
     */
    public void addLoanToUser(final Loan currentLoan) {
	String email = currentUser.getEmail();
	String password = currentUser.getPassword();
	db.saveLoanToUser(currentUser, currentLoan);
	currentUser = db.getUser(email, password);
	makePages(currentUser.getUserLoans());
    }

    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

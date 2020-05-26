package pages;

import classes.CardSwitcher;
import classes.Loan;
import classes.User;
import handlers.Database;
import handlers.LoggerBudget;
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
import java.util.logging.Level;

import static javax.swing.SwingConstants.TOP;

/**
 * Den grafiska sidan som ger tillgång till användarens nuvarande samt möjligheten att skapa ett nytt lån.
 */
public class MainPage extends JPanel implements Page
{
    private final static int TITLE_FONT_SIZE = 38;
    private final static int LOAN_TITLE_FONT_SIZE = 30;
    private final static int BREAD_FONT_SIZE = 18;
    private final static int TEXT_AREA_COLUMN_SIZE = 20;


    private final static Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private final static Font LOAN_TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, LOAN_TITLE_FONT_SIZE);
    private final static Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private final JButton addNewLoan = new JButton("Lägg till lån");
    private final JLabel titleLbl = new JLabel("*BUDGET*");
    private final JButton logOutBtn = new JButton("Logga ut");
    private static JTabbedPane loanPanes = new JTabbedPane(TOP);

    private final Database db;
    private LoggerBudget mainPageLogger = null;
    private User currentUser = null;


    /**
     * Konstruktor som skapar den grafiska layouten samt sätter en logger för sidan och en switcher som gör övergången till
     * andra sidor möjligt. Samt skapar en databas koppling för att kunna spara de gjorda ändringar.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param logger   Loggerklassen som används för att logga varning/info för sidan.
     */
    public MainPage(CardSwitcher switcher, LoggerBudget logger) {
	mainPageLogger = logger;
	db = new Database(logger);
	setLayout(new MigLayout("fillx"));
	titleLbl.setFont(TITLE_FONT);
	add(titleLbl, "wrap,alignx center,spanx,gap 0 0 20 20");
	add(addNewLoan, "");
	add(logOutBtn, "alignx right,wrap");

	addLoanPage(switcher);
	logOut(switcher);

	add(loanPanes, "grow,pushy,spanx");


    }

    /**
     * Loggar ut användare samt loggar vilken användare som har loggats ut sätter den nuvarande inloggade användare till en
     * "tom" användare.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void logOut(final CardSwitcher switcher) {
	logOutBtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		mainPageLogger.logMsg(Level.INFO, "Loggade ut med email: " + currentUser.getEmail());
		currentUser = new User();
		switchPage(switcher, "logInPage");
	    }
	});
    }

    /**
     * Skickar användare till sidan där nya lån skapas.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void addLoanPage(final CardSwitcher switcher) {
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
     * Skapar de grafiska för varje lån som är skapt av användaren.
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

	title.setFont(LOAN_TITLE_FONT);
	p.add(title, "wrap,alignx left,w 30");

	p.add(loanstartDate);
	p.add(loanendDate, "wrap");

	amount.setFont(BREAD_FONT);
	amounttf.setFont(BREAD_FONT);
	p.add(amount, "alignx center");
	p.add(amounttf, "wrap");

	amount.setFont(BREAD_FONT);
	amounttf.setFont(BREAD_FONT);
	p.add(amount, "alignx center");
	p.add(amounttf, "wrap");

	intrest.setFont(BREAD_FONT);
	intresttf.setFont(BREAD_FONT);
	p.add(intrest, "alignx center");
	p.add(intresttf, "wrap");

	amortization.setFont(BREAD_FONT);
	amortizationtf.setFont(BREAD_FONT);
	p.add(amortization, "alignx center");
	p.add(amortizationtf, "wrap");

	description.setFont(BREAD_FONT);
	descriptionta.setFont(BREAD_FONT);
	descriptionta.setLineWrap(true);
	descriptionta.setWrapStyleWord(true);
	p.add(description, "alignx center");
	p.add(descriptionta, "wrap,spanx");


	return p;
    }

    /**
     * Sätter den nuvarande inloggade "vanliga" användaren.
     *
     * @param loggedInUser den inloggade användaren som sätts från LoginPage
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
     * Lägger till de nyligen skapta lånet till användaren samt gör en ny panel för de lånet. Loggar även den nyligen skapta
     * lånet.
     *
     * @param currentLoan De senaste skapta lånet
     */
    public void addLoanToUser(final Loan currentLoan) {
	String email = currentUser.getEmail();
	String password = currentUser.getPassword();
	db.saveLoanToUser(currentUser, currentLoan);
	mainPageLogger.logMsg(Level.INFO, "La till lån med titel: " + currentLoan.getTitle() + " till användare med email: " +
					  currentUser.getEmail());
	currentUser = db.getUser(email, password);
	makePages(currentUser.getUserLoans());
    }

    /**
     * Interface krav från Page interface som gör det möjligt att byta mellan de olika sidorna.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param newPage  Den nya sidan som övergånge ska gå till.
     */
    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

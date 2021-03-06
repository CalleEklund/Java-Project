package pages;

import handlers.CardSwitcher;
import handlers.ProjectLogger;
import user_loan_classes.Loan;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Den grafiska sidan som skapar ett nytt lån till den nuvarande inloggade användaren.
 */
public class AddLoanPage extends JPanel implements Page
{
    private final static int TEXT_FIELD_COLUMN_SIZE = 15;
    private final static int TEXT_AREA_COLUMN_SIZE = 20;
    private final static int TEXT_AREA_INSETS = 10;

    private final static int TITLE_FONT_SIZE = 38;
    private final static int BREAD_FONT_SIZE = 18;
    private final static Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private final static Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);


    private JLabel errorMessageLabel = new JLabel();

    private UtilDateModel modelStart = new UtilDateModel();
    private JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, new Properties());
    private UtilDateModel modelEnd = new UtilDateModel();
    private JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, new Properties());

    private JDatePickerImpl loanStartDate = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
    private JDatePickerImpl loanEndDate = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());

    private JTextField loanTitle = new JTextField(TEXT_FIELD_COLUMN_SIZE);
    private JTextField loanInterest = new JTextField(8);
    private JTextField loanAmount = new JTextField(TEXT_FIELD_COLUMN_SIZE);
    private JTextField loanAmortization = new JTextField(TEXT_FIELD_COLUMN_SIZE);

    private JTextArea loanDescription = new JTextArea(4, TEXT_AREA_COLUMN_SIZE);

    private JButton addLoanButton;
    private JButton exitButton;

    private ProjectLogger addLoanProjectLogger;

    /**
     * Konstruktor som skapar den grafiska layouten samt sätter en projectLogger för sidan och en switcher som gör övergången till
     * andra sidor möjligt.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param projectLogger   Loggerklassen som används för att logga varning/info för sidan.
     */
    public AddLoanPage(CardSwitcher switcher, ProjectLogger projectLogger) {
	addLoanProjectLogger = projectLogger;

	setLayout(new MigLayout("fillx"));
	modelStart.setValue(Calendar.getInstance().getTime());
	modelEnd.setValue(Calendar.getInstance().getTime());
	final JLabel title = new JLabel("Lägg till lån");
	title.setFont(TITLE_FONT);
	add(title, "skip,alignx center,gap 0 0 20 20");


	exitButton = new JButton("Avsluta");
	toMainPage(switcher);
	add(exitButton, "wrap,alignx right,w 30");

	final JLabel loanTitleLabel = new JLabel("Rubrik: ");
	loanTitleLabel.setFont(BREAD_FONT);
	add(loanTitleLabel, "alignx right,gap 0 0 20 0");
	add(loanTitle, "wrap, h 30");

	final JLabel loanStartDateLabel = new JLabel("Startdatum: ");
	loanStartDateLabel.setFont(BREAD_FONT);
	add(loanStartDateLabel, "alignx right,gap 0 0 20 0");
	add(loanStartDate, "wrap,aligny bottom, h 20");

	final JLabel loanEndDateLabel = new JLabel("Slutdatum: ");
	loanEndDateLabel.setFont(BREAD_FONT);
	add(loanEndDateLabel, "alignx right,gap 0 0 20 0");
	add(loanEndDate, "wrap, aligny bottom,h 20");

	final JLabel loanAmountLabel = new JLabel("Mängd: ");
	loanAmountLabel.setFont(BREAD_FONT);
	add(loanAmountLabel, "alignx right,gap 0 0 20 0");
	add(loanAmount, "wrap, h 30");

	final JLabel loanInterestLabel = new JLabel("Ränta(%): ");
	loanInterestLabel.setFont(BREAD_FONT);
	add(loanInterestLabel, "alignx right,gap 0 0 20 0");
	add(loanInterest, "wrap, h 30");

	final JLabel loanAmortizationLabel = new JLabel("Ammortering(kr): ");
	loanAmortizationLabel.setFont(BREAD_FONT);
	add(loanAmortizationLabel, "alignx right,gap 0 0 20 0");
	add(loanAmortization, "wrap, h 30");

	final JLabel loanDescriptionLabel = new JLabel("Beskrivning:");
	loanDescriptionLabel.setFont(BREAD_FONT);
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	loanDescription.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory
		.createEmptyBorder(TEXT_AREA_INSETS, TEXT_AREA_INSETS, TEXT_AREA_INSETS, TEXT_AREA_INSETS)));
	add(loanDescriptionLabel, "alignx right,gap 0 0 20 0");
	add(loanDescription, "wrap,spanx,alignx right,gapright 60");

	errorMessageLabel.setForeground(Color.RED);
	add(errorMessageLabel, "wrap,alignx center,spanx");

	addLoanButton = new JButton("Lägg till lån");
	mainPageValidated(switcher);
	add(addLoanButton, "spanx,alignx center,gap 0 0 10 0");


    }

    /**
     * Skickar tillbaka användare till startsidan och avbryter skapning av ett nytt lån.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void toMainPage(final CardSwitcher switcher) {
	exitButton.addActionListener(actionEvent -> switchPage(switcher, "mainPage"));
    }

    /**
     * Skickar tillbaka användare med ett nytt lån skapat.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     */
    private void mainPageValidated(final CardSwitcher switcher) {
	addLoanButton.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		if (validateInput()) {
		    switchPage(switcher, "mainPage");
		}
	    }
	});
    }

    /**
     * Validerar input
     *
     * @return True/False beroende på om giltig input eller inte
     */
    private boolean validateInput() {
	double intrest;
	int amount, amortization;
	String title = loanTitle.getText();
	String description = loanDescription.getText();
	LocalDate startDateInput = convertToLocalDate(loanStartDate.getModel());
	LocalDate endDateInput = convertToLocalDate(loanEndDate.getModel());
	try {
	    intrest = Double.parseDouble(loanInterest.getText());
	    amortization = Integer.parseInt(loanAmortization.getText());
	    amount = Integer.parseInt(loanAmount.getText());
	} catch (NumberFormatException e) {
	    errorMessageLabel.setText("Ogiltig inmatning (bokstäver ist för siffror)");
	    addLoanProjectLogger.logMsg(Level.WARNING, "Ogiltig inmatning (bokstäver ist för siffror)");
	    e.printStackTrace();
	    return false;
	}
	if (title.isEmpty() || intrest <= 0 || amortization <= 0 || description.isEmpty() || amount <= 0) {
	    errorMessageLabel.setText("Tomma fält");
	    addLoanProjectLogger.logMsg(Level.WARNING, "Tomma fält");

	    return false;
	} else if (endDateInput.isBefore(startDateInput)) {
	    errorMessageLabel.setText("Start datum före slut datum");
	    addLoanProjectLogger.logMsg(Level.WARNING, "Start datum före slut datum");

	    return false;
	} else if (endDateInput.isEqual(startDateInput)) {
	    errorMessageLabel.setText("Start datum samma som slut datum");
	    addLoanProjectLogger.logMsg(Level.WARNING, "Start datum samma som slut datum");

	    return false;
	}
	return true;
    }

    /**
     * Konverterar DateModel datum till ett LocalDate datum
     *
     * @param date DateModel datum
     * @return LocalDate datum
     */
    private LocalDate convertToLocalDate(DateModel<?> date) {
	int dateYear = date.getYear();
	int dateMonth = date.getMonth();
	int dateDay = date.getDay();
	return LocalDate.of(dateYear, dateMonth, dateDay);
    }

    /**
     * Ger LoanController tillgång till currentLoan
     *
     * @return nuvarnde lån
     */
    public Loan getCurrentLoan()
    {
	String title = loanTitle.getText();
	String description = loanDescription.getText();
	LocalDate startDateInput = convertToLocalDate(loanStartDate.getModel());
	LocalDate endDateInput = convertToLocalDate(loanEndDate.getModel());
	double intrest = Double.parseDouble(loanInterest.getText());
	int amortization = Integer.parseInt(loanAmortization.getText());
	int amount = Integer.parseInt(loanAmount.getText());
	if (validateInput()) {
	    return new Loan(title, description, intrest, amount, amortization, startDateInput, endDateInput);
	} else {
	    return null;
	}

    }

    /**
     * Lägger till en lyssnare för nyskapade lån som behövs från LoanController
     *
     * @param listenForAddLoan Lyssnare för nyskapde lån
     */
    public void addAddLoanListener(ActionListener listenForAddLoan) {
	addLoanButton.addActionListener(listenForAddLoan);
    }

    /**
     * Interface krav från Page interface som gör det möjligt att byta mellan de olika sidorna.
     *
     * @param switcher Cardlayout för att kunna byta mellan sidorna.
     * @param newPage Den nya sidan som övergånge ska gå till.
     */
    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }

}


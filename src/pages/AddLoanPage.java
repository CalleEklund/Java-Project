package pages;

import classes.CardSwitcher;
import classes.Loan;
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

/**
 * Lägg till ett lån
 */
public class AddLoanPage extends JPanel
{
    final static private int TEXT_FIELD_COLUMN_SIZE = 15;
    final static private int TEXT_AREA_COLUMN_SIZE = 20;



    private JLabel errorMessagelbl = new JLabel();

    private UtilDateModel modelStart = new UtilDateModel();
    private JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, new Properties());
    private UtilDateModel modelEnd = new UtilDateModel();
    private JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, new Properties());

    private JDatePickerImpl loanstartDate = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
    private JDatePickerImpl loanendDate = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());

    private JTextField loanTitle = new JTextField(TEXT_FIELD_COLUMN_SIZE);
    private JTextField loanInterest = new JTextField(8);
    private JTextField loanAmount = new JTextField(TEXT_FIELD_COLUMN_SIZE);
    private JTextField loanAmortization = new JTextField(TEXT_FIELD_COLUMN_SIZE);

    private JTextArea loanDescription = new JTextArea(4, TEXT_AREA_COLUMN_SIZE);

    private JButton addLoan = new JButton("Lägg till lån");

    /**
       * Layout init.
       * @param switcher cardlayout för att kunna byta mellan sidorna
       */
    public AddLoanPage(CardSwitcher switcher) {
	setLayout(new MigLayout("fillx"));
	modelStart.setValue(Calendar.getInstance().getTime());
	modelEnd.setValue(Calendar.getInstance().getTime());
	final JLabel title = new JLabel("Lägg till lån");
	final int titleFontSize = 38;
	final Font titleFont = new Font(Font.SERIF, Font.PLAIN, titleFontSize);
	title.setFont(titleFont);
	add(title, "skip,alignx center,gap 0 0 20 20");

	Action exitToMainPage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("mainPage");
	    }
	};
	final JButton exit = new JButton("Avsluta");
	exit.addActionListener(exitToMainPage);
	add(exit, "wrap,alignx right,w 30");

	final JLabel loanTitlelbl = new JLabel("Rubrik: ");
	final int breadFontSize = 18;
	final Font breadFont = new Font(Font.SERIF, Font.PLAIN, breadFontSize);
	loanTitlelbl.setFont(breadFont);
	add(loanTitlelbl, "alignx right,gap 0 0 20 0");
	add(loanTitle, "wrap, h 30");

	final JLabel loanStartDatelbl = new JLabel("Startdatum: ");
	loanStartDatelbl.setFont(breadFont);
	add(loanStartDatelbl, "alignx right,gap 0 0 20 0");
	add(loanstartDate, "wrap,aligny bottom, h 20");

	final JLabel loanEndDatelbl = new JLabel("Slutdatum: ");
	loanEndDatelbl.setFont(breadFont);
	add(loanEndDatelbl, "alignx right,gap 0 0 20 0");
	add(loanendDate, "wrap, aligny bottom,h 20");

	final JLabel loanAmountlbl = new JLabel("Mängd: ");
	loanAmountlbl.setFont(breadFont);
	add(loanAmountlbl, "alignx right,gap 0 0 20 0");
	add(loanAmount, "wrap, h 30");

	final JLabel loanInterestlbl = new JLabel("Ränta(%): ");
	loanInterestlbl.setFont(breadFont);
	add(loanInterestlbl, "alignx right,gap 0 0 20 0");
	add(loanInterest, "wrap, h 30");

	final JLabel loanAmortizationlbl = new JLabel("Ammortering(kr): ");
	loanAmortizationlbl.setFont(breadFont);
	add(loanAmortizationlbl, "alignx right,gap 0 0 20 0");
	add(loanAmortization, "wrap, h 30");

	final JLabel loanDescriptionlbl = new JLabel("Beskrivning:");
	loanDescriptionlbl.setFont(breadFont);
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	loanDescription.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	add(loanDescriptionlbl, "alignx right,gap 0 0 20 0");
	add(loanDescription, "wrap,spanx,alignx right,gapright 60");

	errorMessagelbl.setForeground(Color.RED);
	add(errorMessagelbl, "wrap,alignx center,spanx");
	Action toMainPage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		if (validateInput()) {
		    switcher.switchTo("mainPage");
		}
	    }
	};
	addLoan.addActionListener(toMainPage);
	add(addLoan, "spanx,alignx center,gap 0 0 10 0");

	/**
	 * TODO:
	 *  - ta bort innan inlämmning
	 */
	int test = 1000;
	loanTitle.setText("Test");
	loanDescription.setText("testdesc");
	loanAmount.setText(String.valueOf(test));
	loanAmortization.setText(String.valueOf(test));
	loanInterest.setText(String.valueOf(test));

    }

    /**
     * Validerar input
     * @return True/False beroende på om giltig input eller inte
     */
    public boolean validateInput() {
	double intrest;
	int amount, amortization;
	String title = loanTitle.getText();
	String description = loanDescription.getText();
	LocalDate startDateInput = convertToLocalDate(loanstartDate.getModel());
	LocalDate endDateInput = convertToLocalDate(loanendDate.getModel());
	try {
	    intrest = Double.parseDouble(loanInterest.getText());
	    amortization = Integer.parseInt(loanAmortization.getText());
	    amount = Integer.parseInt(loanAmount.getText());
	} catch (NumberFormatException e) {
	    errorMessagelbl.setText("Ogiltig inmatning (bokstäver ist för siffror)");
	    System.out.println("Error: " + e);
	    return false;
	}
	if (title.isEmpty() || intrest <= 0 || amortization <= 0 || description.isEmpty() || amount <= 0) {
	    errorMessagelbl.setText("Tomma fält");
	    return false;
	} else if (endDateInput.isBefore(startDateInput)) {
	    errorMessagelbl.setText("Start datum före slut datum");
	    return false;
	} else if (endDateInput.isEqual(startDateInput)) {
	    errorMessagelbl.setText("Start datum samma som slut datum");
	    return false;
	}
	return true;
    }

    /**
     * Konverterar DateModel datum till ett LocalDate datum
     * @param date DateModel datum
     * @return LocalDate datum
     */
    public LocalDate convertToLocalDate(DateModel<?> date) {
	int dateYear = date.getYear();
	int dateMonth = date.getMonth();
	int dateDay = date.getDay();
	return LocalDate.of(dateYear, dateMonth, dateDay);
    }

    /**
     * Ger LoanController tillgång till currentLoan
     * @return nuvarnde lån
     */
    public Loan getCurrentLoan()
    {
	String title = loanTitle.getText();
	String description = loanDescription.getText();
	LocalDate startDateInput = convertToLocalDate(loanstartDate.getModel());
	LocalDate endDateInput = convertToLocalDate(loanendDate.getModel());
	double intrest = Double.parseDouble(loanInterest.getText());
	int amortization = Integer.parseInt(loanAmortization.getText());
	int amount = Integer.parseInt(loanAmount.getText());
	if (validateInput()) {
	    return new Loan(title, description, intrest, amount, amortization, startDateInput, endDateInput);
	} else {
	    return null;
	}

    }

    public void addAddLoanListener(ActionListener listenForAddLoan) {
	addLoan.addActionListener(listenForAddLoan);
    }
}


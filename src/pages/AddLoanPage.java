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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class AddLoanPage extends JPanel
{
    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 18);
    Font small = new Font(Font.SERIF, Font.PLAIN, 14);

    final JLabel title = new JLabel("Lägg till lån");
    final JLabel loanTitlelbl = new JLabel("Rubrik: ");
    final JLabel loanStartDatelbl = new JLabel("Startdatum: ");
    final JLabel loanEndDatelbl = new JLabel("Slutdatum: ");
    final JLabel loanAmountlbl = new JLabel("Mängd: ");
    final JLabel loanAmortizationlbl = new JLabel("Ammortering(kr): ");
    final JLabel loanInterestlbl = new JLabel("Ränta(%): ");
    final JLabel loanDescriptionlbl = new JLabel("Beskrivning:");
    final JLabel errorMessagelbl = new JLabel();

    UtilDateModel modelStart = new UtilDateModel();
    JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, new Properties());
    UtilDateModel modelEnd = new UtilDateModel();
    JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, new Properties());

    final JDatePickerImpl loanstartDate = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
    final JDatePickerImpl loanendDate = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());

    final JTextField loanTitle = new JTextField(15);
    final JTextField loanInterest = new JTextField(8);
    final JTextField loanAmount = new JTextField(15);
    final JTextField loanAmortization = new JTextField(15);

    final JTextArea loanDescription = new JTextArea(4, 20);

    final JButton exit = new JButton("Avsluta");
    final JButton addLoan = new JButton("Lägg till lån");

    public AddLoanPage(CardSwitcher switcher) {
	setLayout(new MigLayout("fillx"));
	modelStart.setValue(Calendar.getInstance().getTime());
	modelEnd.setValue(Calendar.getInstance().getTime());
	title.setFont(titleFont);
	add(title, "skip,alignx center,gap 0 0 20 20");

	Action exitToMainPage = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		switcher.switchTo("mainPage");
	    }
	};
	exit.addActionListener(exitToMainPage);
	add(exit, "wrap,alignx right,w 30");

	loanTitlelbl.setFont(breadFont);
	add(loanTitlelbl, "alignx right,gap 0 0 20 0");
	add(loanTitle, "wrap, h 30");

	loanStartDatelbl.setFont(breadFont);
	add(loanStartDatelbl, "alignx right,gap 0 0 20 0");
	add(loanstartDate, "wrap,aligny bottom, h 20");

	loanEndDatelbl.setFont(breadFont);
	add(loanEndDatelbl, "alignx right,gap 0 0 20 0");
	add(loanendDate, "wrap, aligny bottom,h 20");

	loanAmountlbl.setFont(breadFont);
	add(loanAmountlbl, "alignx right,gap 0 0 20 0");
	add(loanAmount, "wrap, h 30");

	loanInterestlbl.setFont(breadFont);
	add(loanInterestlbl, "alignx right,gap 0 0 20 0");
	add(loanInterest, "wrap, h 30");

	loanAmortizationlbl.setFont(breadFont);
	add(loanAmortizationlbl, "alignx right,gap 0 0 20 0");
	add(loanAmortization, "wrap, h 30");

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
		System.out.println(validateInput());
		if (validateInput()) {
		    switcher.switchTo("mainPage");
		}
	    }
	};
	addLoan.addActionListener(toMainPage);
	add(addLoan, "spanx,alignx center,gap 0 0 10 0");

	int test = 1000;
	loanTitle.setText("Test");
	loanDescription.setText("testdesc");
	loanAmount.setText(String.valueOf(test));
	loanAmortization.setText(String.valueOf(test));
	loanInterest.setText(String.valueOf(test));
//	loanstartDate.getModel().setDate(2000, 10, 10);
//	loanendDate.getModel().setDate(2000, 10, 10);

    }

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

    public LocalDate convertToLocalDate(DateModel date) {
	int dateYear = date.getYear();
	int dateMonth = date.getMonth();
	int dateDay = date.getDay();
	return LocalDate.of(dateYear, dateMonth, dateDay);
    }

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

    public static void main(String[] args) {
	JFrame frame = new JFrame();

//        frame.add(new pages.AddLoanPage(switcher));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(450, 600);
	frame.setResizable(false);
	frame.setVisible(true);

    }


}

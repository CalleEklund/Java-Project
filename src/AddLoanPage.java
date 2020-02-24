import javafx.scene.control.DatePicker;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class AddLoanPage extends JPanel
    /**
     * TODO:
     * 	- Lägg till summa som ett fält
     * 	- Fortsätt med validering
     * **/
{
    Font titleFont = new Font(Font.SERIF, Font.PLAIN, 38);
    Font breadFont = new Font(Font.SERIF, Font.PLAIN, 18);
    Font small = new Font(Font.SERIF, Font.PLAIN, 14);

    final JLabel title = new JLabel("Lägg till lån");
    final JLabel loanTitlelbl = new JLabel("Rubrik: ");
    final JLabel loanStartDatelbl = new JLabel("Startdatum: ");
    final JLabel loanEndDatelbl = new JLabel("Slutdatum: ");
    final JLabel loanAmortizationlbl = new JLabel("Ammortering(kr): ");
    final JLabel loanInterestlbl = new JLabel("Ränta(%): ");
    final JLabel loanDescriptionlbl = new JLabel("Beskrivning:");
    final JLabel errorMessagelbl = new JLabel();


    UtilDateModel modelStart = new UtilDateModel();
    JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, new Properties());
    UtilDateModel modelEnd = new UtilDateModel();
    JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, new Properties());

    final JDatePickerImpl startDate = new JDatePickerImpl(datePanelStart, new DateComponentFormatter());
    final JDatePickerImpl endDate = new JDatePickerImpl(datePanelEnd, new DateComponentFormatter());

    final JTextField loanTitle = new JTextField(15);
    final JTextField loanInterest = new JTextField(8);
    final JTextField loanAmortization = new JTextField(15);

    final JTextArea loanDescription = new JTextArea(4, 20);

    final JButton exit = new JButton("Avsluta");
    final JButton addLoan = new JButton("Lägg till lån");

    public AddLoanPage(CardSwitcher switcher) {
	setLayout(new MigLayout("fillx"));
	//model.setSelected(true);

	title.setFont(titleFont);
	add(title, "wrap,spanx ,alignx center,gap 0 0 20 20");

	loanTitlelbl.setFont(breadFont);
	add(loanTitlelbl, "alignx right,gap 0 0 20 0");
	add(loanTitle, "wrap, h 30");

	loanStartDatelbl.setFont(breadFont);
	add(loanStartDatelbl, "alignx right,gap 0 0 20 0");
	add(startDate, "wrap,aligny bottom, h 20");

	loanEndDatelbl.setFont(breadFont);
	add(loanEndDatelbl, "alignx right,gap 0 0 20 0");
	add(endDate, "wrap, aligny bottom,h 20");

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
		if (validateInput()) {
		    switcher.switchTo("mainPage");
		}
	    }
	};
	addLoan.addActionListener(toMainPage);
	add(addLoan, "spanx,alignx center,gap 0 0 10 0");


    }

    public boolean validateInput() {
	String title = loanTitle.getText();
	String intrest = loanInterest.getText();
	String amortization = loanAmortization.getText();
	String description = loanDescription.getText();
	LocalDate startDateInput = convertToLocalDate(startDate.getModel());
	LocalDate endDateInput = convertToLocalDate(endDate.getModel());
	try {
	    Integer.parseInt(intrest);
	    Integer.parseInt(amortization);
	}catch (NumberFormatException e){
	    errorMessagelbl.setText("Ogiltig inmatning (bokstäver ist för siffror)");
	    return false;
	}
	if (title.isEmpty() || intrest.isEmpty() || amortization.isEmpty() || description.isEmpty()) {
	    errorMessagelbl.setText("Tomma fält");
	    return false;
	} else if (endDateInput.isBefore(startDateInput)) {
	    errorMessagelbl.setText("Start datum före slut datum");
	    return false;
	}else if(endDateInput.isEqual(startDateInput)){
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

    public static void main(String[] args) {
	JFrame frame = new JFrame();

//        frame.add(new AddLoanPage(switcher));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(450, 600);
	frame.setResizable(false);
	frame.setVisible(true);

    }
}


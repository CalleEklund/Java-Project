import classes.User;
import classes.Loan;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;


public class test extends JPanel
{
    static User u = null;
    static Font titleFont = new Font(Font.SERIF, Font.PLAIN, 30);
    static Font breadFont = new Font(Font.SERIF, Font.PLAIN, 18);

    final JLabel titlelbl = new JLabel("*BUDGET*");
    final JButton logOutbtn = new JButton("Logga ut");



    // = new JTabbedPane(JTabbedPane.TOP);
    public test() {
	setLayout(new MigLayout("fillx"));
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,alignx center,gap 0 0 20 20");
	add(logOutbtn, "wrap,alignx right");
	add(makePages(u.getUserLoanshm()),"grow,pushy");

    }



    private static JTabbedPane makePages(HashMap<String, Loan> userLoans) {
	JTabbedPane loanPanes = new JTabbedPane(JTabbedPane.TOP);
	JPanel loanPanel;
	int index = 0;
	for (String key : userLoans.keySet()) {
	    loanPanel = makeLoanPanel(userLoans.get(key));
	    Loan currentLoan = userLoans.get(key);
	    loanPanes.insertTab(currentLoan.getTitle(),null,loanPanel,null,index);
	    index++;
	}

	return loanPanes;
    }
    private static JPanel makeLoanPanel(Loan l){
        JPanel p = new JPanel();
        p.setLayout(new MigLayout("fillx,debug"));

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
	JLabel description =  new JLabel("Beskrivning");

	JTextField amounttf = new JTextField(String.valueOf(l.getAmount()));
	JTextField intresttf = new JTextField(String.valueOf(l.getIntrest()));
	JTextField amortizationtf = new JTextField(String.valueOf(l.getAmortization()));
	JTextArea descriptionta =  new JTextArea(l.getDescription(),4,20);

	amounttf.setEditable(false);
	intresttf.setEditable(false);
	amortizationtf.setEditable(false);
	descriptionta.setEditable(false);

	title.setFont(titleFont);
	p.add(title,"wrap,alignx left,w 30");

	p.add(loanstartDate);
	p.add(loanendDate,"wrap");

	amount.setFont(breadFont);
	amounttf.setFont(breadFont);
	p.add(amount,"alignx center");
	p.add(amounttf,"wrap");

	amount.setFont(breadFont);
	amounttf.setFont(breadFont);
	p.add(amount,"alignx center");
	p.add(amounttf,"wrap");

	intrest.setFont(breadFont);
	intresttf.setFont(breadFont);
	p.add(intrest,"alignx center");
	p.add(intresttf,"wrap");

	amortization.setFont(breadFont);
	amortizationtf.setFont(breadFont);
	p.add(amortization,"alignx center");
	p.add(amortizationtf,"wrap");

	description.setFont(breadFont);
	descriptionta.setFont(breadFont);
	descriptionta.setLineWrap(true);
	descriptionta.setWrapStyleWord(true);
	p.add(description,"alignx center");
	p.add(descriptionta,"wrap,spanx");







        return p;
    }
    private static JPanel makePanel(String text) {
	JPanel p = new JPanel();
	p.add(new Label(text));
	p.setLayout(new GridLayout(1, 1));
	return p;
    }
    public static void main(String[] args) {
	u = new User("Calle", "test@gmail.com", "losen");
	u.addUserLoanshm(new Loan("test1", "testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1testdesc1", 2, 1000, 100, LocalDate.of(2000, 10, 10), LocalDate.now()));
	u.addUserLoanshm(new Loan("test2", "testdesc2", 2, 1000, 100, LocalDate.of(2000, 10, 10), LocalDate.now()));

	JFrame f = new JFrame();
	f.add(new test());
	f.setSize(600, 600);
	f.setVisible(true);
    }
}

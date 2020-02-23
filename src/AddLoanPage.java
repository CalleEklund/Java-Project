import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLoanPage extends JPanel {
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

    final JXDatePicker startDate = new JXDatePicker();
    final JXDatePicker endDate = new JXDatePicker();

    final JTextField loanTitle = new JTextField(15);
    final JTextField loanInterest = new JTextField(8);
    final JTextField loanAmortization = new JTextField(15);

    final JTextArea loanDescription = new JTextArea(4, 20);

    final JButton exit = new JButton("Avsluta");
    final JButton addLoan = new JButton("Lägg till lån");

    public AddLoanPage(CardSwitcher switcher) {
        setLayout(new MigLayout("fillx"));


        title.setFont(titleFont);
        add(title, "wrap,spanx ,alignx center,gap 0 0 20 20");

        loanTitlelbl.setFont(breadFont);
        add(loanTitlelbl, "alignx right,gap 0 0 20 0");
        add(loanTitle, "wrap, h 30");

        startDate.setDate(Calendar.getInstance().getTime());
        startDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        loanStartDatelbl.setFont(breadFont);
        add(loanStartDatelbl, "alignx right,gap 0 0 20 0");
        add(startDate, "wrap,aligny bottom, h 20");

        endDate.setDate(Calendar.getInstance().getTime());
        endDate.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
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
        loanDescription.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(loanDescriptionlbl, "alignx right,gap 0 0 20 0");
        add(loanDescription, "wrap,spanx,alignx right,gapright 60");


        Action toMainPage = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switcher.switchTo("mainPage");
            }
        };
        addLoan.addActionListener(toMainPage);
        add(addLoan, "spanx,alignx center,gap 0 0 10 0");


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


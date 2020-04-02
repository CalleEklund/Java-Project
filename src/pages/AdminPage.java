package pages;

import classes.CardSwitcher;
import classes.User;
import classes.UserTypes;
import savehandlers.Database;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminPage extends JPanel implements Page
{
    final static private int TITLE_FONT_SIZE = 38;
    final static private int BREAD_FONT_SIZE = 18;
    public static final int ROW_HEIGHT = 20;

    private static Font titleFont = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    private static Font breadFont = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private final JLabel titlelbl = new JLabel("*BUDGET ADMIN*");
    private final JButton logOutbtn = new JButton("Logga ut");
    private final JButton savebtn = new JButton("Spara ändringar");

    private final JButton showData = new JButton("Visa användare");
    private JTable table = null;
    private JScrollPane tableCont = null;
    private JComboBox<String> comboBox = null;

    private User currentAdmin = null;
    private final Database db;

    private List<User> data = null;

    /**
     * Konstruktor
     *
     * @param switcher Används från CardSwither klassen som används för att byta sida inom applikationen
     */
    public AdminPage(CardSwitcher switcher) {

	db = new Database();
	setLayout(new MigLayout("fillx, debug"));
	titlelbl.setFont(titleFont);
	add(titlelbl, "wrap,alignx center,spanx,gap 0 0 20 20");

	final JPanel btns = new JPanel();
	btns.add(showData);
	btns.add(savebtn);
	btns.add(logOutbtn);
	add(btns, "alignx right,wrap");

	logOut(switcher);

	showData.addActionListener(printTable);
	savebtn.addActionListener(saveData);



    }

    private void logOut(final CardSwitcher switcher) {
	logOutbtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {

	        switchPage(switcher, "logInPage");
	    }
	});
    }


    /**
     * Kallas när knappen saveData klickas hämtar all data från tabellen för att sedan jämföra mot den gamla för att inte
     * belasta databasen med onödig data.
     */
    private ActionListener saveData = new ActionListener()
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    ArrayList<User> changedData = new ArrayList<>();
	    if (table != null) {
		for (int i = 0; i < table.getRowCount(); i++) {
		    String id = (String) table.getValueAt(i, 0);
		    String name = (String) table.getValueAt(i, 1);
		    String email = (String) table.getValueAt(i, 2);
		    String password = (String) table.getValueAt(i, 3);
		    UserTypes userType;
		    if (UserTypes.ORDINARY.equals(table.getValueAt(i, 4))) {
			userType = UserTypes.ORDINARY;
		    } else {
			userType = UserTypes.ADMIN;
		    }
		    User curr = new User(id, name, email, password, userType);
		    changedData.add(new User(id, name, email, password, userType));
		}
		changedData = getNoDuplicate(data, changedData);
	    }

	    db.updateData(changedData);

	}
    };

    /**
     * Jämför den gamla tabellen mot den nya
     *
     * @param oldData Den gamla tabellens data (ArrayList av User)
     * @param newData Den nya tabellens data (ArrayList av User)
     * @return returnerar en ArrayList med den ändraden datan
     */
    private ArrayList<User> getNoDuplicate(List<User> oldData, ArrayList<User> newData) {
	ArrayList<User> temp = new ArrayList<>();
	String t = "test";
	for (int i = 0; i < oldData.size(); i++) {
	    if (!oldData.get(i).compareTo(newData.get(i))) {
		temp.add(newData.get(i));
	    }
	}
	return temp;
    }

    /**
     * Printar den tabellen som fylls med data från databasen, sätter även användarns id och lån otilgängliga, möjligt att det
     * blir en feature senare.
     */
    private ActionListener printTable = new ActionListener()
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {

	    String[] columnNames = { "ID", "Namn", "Email", "Lösenord", "Användartyp", "Antal Lån" };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0)
	    {
		@Override public boolean isCellEditable(final int row, final int column) {
		    return column == 1 || column == 2 || column == 3 || column == 4;
		}
	    };


	    data = db.getAllData();
	    Object[] rowData = new Object[7];
	    for (int i = 0; i < db.getAllData().size(); i++) {
		rowData[0] = data.get(i).getUid();
		rowData[1] = data.get(i).getName();
		rowData[2] = data.get(i).getEmail();
		rowData[3] = data.get(i).getPassword();
		rowData[4] = data.get(i).getUserType();
		rowData[5] = data.get(i).getUserLoans().size();
		rowData[6] = new JButton("Ändra");
		tableModel.addRow(rowData);

	    }
	    table.setRowHeight(ROW_HEIGHT);
	    table.setModel(tableModel);

	    TableColumn userTypeCol = table.getColumnModel().getColumn(4);
	    comboBox = new JComboBox<>();
	    comboBox.addItem("ORDINARY");
	    comboBox.addItem("ADMIN");
	    userTypeCol.setCellEditor(new DefaultCellEditor(comboBox));

	    table.setFillsViewportHeight(true);
	    tableCont = new JScrollPane(table);
	    repaint();
	    revalidate();
	    add(tableCont);

	}
    };

    /**
     * Sätter den nuvarande inloggade admin användaren
     *
     * @param loggedinUser den inloggade användaren som sätts från LoginPage
     */
    public void setCurrentAdmin(User loggedinUser) {
	currentAdmin = loggedinUser;
    }


    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

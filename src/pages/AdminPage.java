package pages;

import classes.CardSwitcher;
import classes.User;
import classes.UserTypes;
import classes.Validator;
import handlers.Database;

import handlers.LoggerBudget;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class AdminPage extends JPanel implements Page
{
    final static private int TITLE_FONT_SIZE = 38;
    final static private int BREAD_FONT_SIZE = 18;
    final static private int ROW_HEIGHT = 20;
    final static private int Y_INSETS = 7;
    final static private int X_INSETS = 1;
    final static int ID_INDEX = 0;
    final static int NAME_INDEX = 1;
    final static int EMAIL_INDEX = 2;
    final static int PASSWORD_INDEX = 3;
    final static int USER_TYPE_INDEX = 4;

    final static private Font TITLE_FONT = new Font(Font.SERIF, Font.PLAIN, TITLE_FONT_SIZE);
    final static private Font BREAD_FONT = new Font(Font.SERIF, Font.PLAIN, BREAD_FONT_SIZE);

    private final JLabel titlelbl = new JLabel("*BUDGET ADMIN*");

    private final JButton logOutbtn = new JButton("Logga ut");
    private final JButton savebtn = new JButton("Spara ändringar");
    private final JButton showDatabtn = new JButton("Visa användare");
    private final JButton addUserbtn = new JButton("Ny användare");
    private final JButton removeUserbtn = new JButton("Ta bort användare");


    private JTable table = new JTable();
    private JScrollPane tableCont = null;
    private JComboBox<String> comboBox = null;

    private JPanel mainCont = new JPanel();

    private User currentAdmin = null;
    private final Database db;
    private Validator validator = new Validator();
    private LoggerBudget adminLogger = null;


    private List<User> data = null;

    /**
     * Konstruktor Den klass används för att administera de vanliga användarna som är registrerade i applikationen, man kan även
     * lägga till samt ta bort användare.
     *
     * @param switcher Används från CardSwither klassen som används för att byta sida inom applikationen
     */
    public AdminPage(CardSwitcher switcher, LoggerBudget logger) {
	adminLogger = logger;
	db = new Database(logger);
	setLayout(new MigLayout("fillx"));
	titlelbl.setFont(TITLE_FONT);
	add(titlelbl, "wrap,alignx center,spanx,gap 0 0 20 20");

	final JPanel btns = new JPanel();
	btns.add(addUserbtn);
	addUserbtn.setMargin(new Insets(X_INSETS, Y_INSETS, X_INSETS, Y_INSETS));
	btns.add(removeUserbtn);
	removeUserbtn.setMargin(new Insets(X_INSETS, Y_INSETS, X_INSETS, Y_INSETS));
	btns.add(showDatabtn);
	showDatabtn.setMargin(new Insets(X_INSETS, Y_INSETS, X_INSETS, Y_INSETS));
	btns.add(savebtn);
	savebtn.setMargin(new Insets(X_INSETS, Y_INSETS, X_INSETS, Y_INSETS));
	btns.add(logOutbtn);
	logOutbtn.setMargin(new Insets(X_INSETS, Y_INSETS, X_INSETS, Y_INSETS));
	add(btns, "spanx,wrap,alignx right");

	removeUserbtn.setEnabled(false);
	addUserbtn.setEnabled(false);

	logOut(switcher);

	add(mainCont, "spanx");
	showDatabtn.addActionListener(printTable);
	savebtn.addActionListener(saveData);
	addUserbtn.addActionListener(addNewUser);
	removeUserbtn.addActionListener(removeUser);


    }

    /**
     * Kallas på när removeUserbtn knappen klickas som sedan skapar en JOptionpane som ger dig valet vilken använder du vill
     * readera baserat på id. Kallar sedan på databasfunktionen som raderar användarande från databasen.
     */
    private ActionListener removeUser = new ActionListener()
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    Object[] possibilities = getCurrentIds().toArray();
	    Object first = "Inga användare";
	    if (possibilities.length > 1) {
		first = possibilities[0];
	    }
	    String s = String.valueOf(JOptionPane.showInputDialog(null, "Välj ett konto med det id du vill ta bort",
								  "Ta bort användare", JOptionPane.PLAIN_MESSAGE, null,
								  possibilities, first));

	    User u = (User) getUserFromTable(s)[0];
	    int index = (int) getUserFromTable(s)[1];

	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    if (possibilities.length != 0 && s != null) {
		model.removeRow(index);
		db.removeUser(u);
		adminLogger.logMsg(Level.INFO, "Tog bort användare med email: " + u.getEmail());

	    }
	}
    };
    /**
     * Lägger till en ny rad i tabellen vilket gör det möjligt att skapa en ny användare genom att fylla i inloggningsuppgifter
     */
    private ActionListener addNewUser = new ActionListener()
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    List<Integer> ids = getCurrentIds();
	    int newId = Collections.max(ids) + 1;
	    model.insertRow(table.getRowCount(), new Object[] { newId, "", "", "", UserTypes.ORDINARY, 0 });
	    User u = new User(String.valueOf(newId), "", "", "", UserTypes.ORDINARY);
	    data.add(u);

	}
    };

    /**
     * Hämtar de ids som finns i tabellen, används för att illustrera vilket id den ny användaren skulle få, samt vilka ids som
     * finns tillgängliga för radering
     *
     * @return En lista av de tillgängliga id:n
     */
    private List<Integer> getCurrentIds() {
	List<Integer> ids = new ArrayList<>();
	DefaultTableModel model = (DefaultTableModel) table.getModel();

	for (int i = 0; i < table.getRowCount(); i++) {
	    int currentId = Integer.parseInt(model.getValueAt(i, ID_INDEX).toString());
	    ids.add(currentId);
	}
	return ids;
    }

    /**
     * Tar bort tabellen, samt skickar användare till inloggningssidan.
     *
     * @param switcher
     */
    private void logOut(final CardSwitcher switcher) {
	logOutbtn.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent actionEvent) {
		mainCont.removeAll();
		mainCont.revalidate();
		switchPage(switcher, "logInPage");
		adminLogger.logMsg(Level.INFO, "Loggade ut från adminsida med email: " + currentAdmin.getEmail());
	    }
	});
    }

    /**
     * Hämtar en användare i från tabellen givet ett sökt id
     *
     * @param searchedId den sökta id:t
     * @return en använder i formen av klassen User
     */
    private Object[] getUserFromTable(String searchedId) {
	User searchedUser = null;
	int index = -1;

	for (int i = 0; i < table.getRowCount(); i++) {
	    if (table.getValueAt(i, 0).toString().equals(searchedId)) {
		String id = table.getValueAt(i, ID_INDEX).toString();
		String name = table.getValueAt(i, NAME_INDEX).toString();
		String email = table.getValueAt(i, EMAIL_INDEX).toString();
		String password = table.getValueAt(i, PASSWORD_INDEX).toString();
		UserTypes userType;
		if (table.getValueAt(i, USER_TYPE_INDEX).toString().equals(UserTypes.ORDINARY)) {
		    userType = UserTypes.ORDINARY;
		} else {
		    userType = UserTypes.ADMIN;
		}
		index = i;
		searchedUser = new User(id, name, email, password, userType);
	    }

	}
	return new Object[] { searchedUser, index };
    }

    /**
     * Kollar så att det inte finns några tomma rader i tabellen.
     *
     * @return true/false
     */
    private boolean validCheck() {
	if (table.getCellEditor() != null) {
	    table.getCellEditor().stopCellEditing();
	}
	for (int i = 0; i < table.getRowCount(); i++) {
	    for (int j = 0; j < table.getColumnCount(); j++) {
		String value = table.getValueAt(i, j).toString();
		if (value.trim().isEmpty()) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Kallas när knappen saveData klickas hämtar all data från tabellen för att sedan jämföra mot den gamla för att inte
     * belasta databasen med onödig data.
     */
    private ActionListener saveData = new ActionListener()
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {

	    if (!validCheck()) {
		JOptionPane.showMessageDialog(null, "Det finns tomma fält");
	    } else {
		ArrayList<User> changedData = new ArrayList<>();
		if (table != null) {
		    for (int i = 0; i < table.getRowCount(); i++) {
			String id = table.getValueAt(i, ID_INDEX).toString();
			String name = (String) table.getValueAt(i, NAME_INDEX);
			String email = (String) table.getValueAt(i, EMAIL_INDEX);
			String password = (String) table.getValueAt(i, PASSWORD_INDEX);
			UserTypes userType;
			if (UserTypes.ORDINARY.equals(table.getValueAt(i, USER_TYPE_INDEX))) {
			    userType = UserTypes.ORDINARY;
			} else {
			    userType = UserTypes.ADMIN;
			}
			User curr = new User(id, name, email, password, userType);
			changedData.add(new User(id, name, email, password, userType));
		    }
		    changedData = getNoDuplicate(data, changedData);
		}
		List<String> updatedDataIndexs = new ArrayList<>();
		for (User updatedUser : changedData) {
		    updatedDataIndexs.add(updatedUser.getEmail());
		}
		List<String> wrongEmailId = new ArrayList<>();
		for (User u : changedData) {
		    if (!validator.validateEmail(u.getEmail())) {
			wrongEmailId.add(u.getUid());
		    }
		}
		if (!wrongEmailId.isEmpty()) {

		    int dialogResult = JOptionPane.showConfirmDialog(null,
								     "Det finns felaktiga emails vid id: " + wrongEmailId +
								     ".\nVill du fortsätta?", "Felaktiga Emails",
								     JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		    if (dialogResult == JOptionPane.YES_OPTION) {
			db.updateData(changedData);
			adminLogger.logMsg(Level.INFO, "Uppdaterade användar tabellen vid email: " + updatedDataIndexs);
		    }
		} else {
		    db.updateData(changedData);
		    adminLogger.logMsg(Level.INFO, "Uppdaterade användar tabellen vid email: " + updatedDataIndexs);
		}
	    }


	}
    };

    /**
     * Jämför den gamla tabellen mot den nya och tar bort den datan som inte ändras
     *
     * @param oldData Den gamla tabellens data (ArrayList av User)
     * @param newData Den nya tabellens data (ArrayList av User)
     * @return returnerar en ArrayList med den ändraden datan
     */
    private ArrayList<User> getNoDuplicate(List<User> oldData, ArrayList<User> newData) {
	ArrayList<User> temp = new ArrayList<>();
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
		    return column == NAME_INDEX || column == EMAIL_INDEX || column == PASSWORD_INDEX || column == USER_TYPE_INDEX;
		}
	    };

	    addUserbtn.setEnabled(true);
	    removeUserbtn.setEnabled(true);

	    data = db.getAllData();
	    Object[] rowData = new Object[6];
	    for (int i = 0; i < db.getAllData().size(); i++) {
		rowData[ID_INDEX] = data.get(i).getUid();
		rowData[NAME_INDEX] = data.get(i).getName();
		rowData[EMAIL_INDEX] = data.get(i).getEmail();
		rowData[PASSWORD_INDEX] = data.get(i).getPassword();
		rowData[USER_TYPE_INDEX] = data.get(i).getUserType();
		rowData[5] = data.get(i).getUserLoans().size();
		tableModel.addRow(rowData);

	    }
	    table.setRowHeight(ROW_HEIGHT);
	    table.setModel(tableModel);

	    TableColumn userTypeCol = table.getColumnModel().getColumn(USER_TYPE_INDEX);
	    comboBox = new JComboBox<>();
	    comboBox.addItem("ORDINARY");
	    comboBox.addItem("ADMIN");
	    userTypeCol.setCellEditor(new DefaultCellEditor(comboBox));

	    table.setFillsViewportHeight(true);
	    tableCont = new JScrollPane(table);
	    repaint();
	    revalidate();
	    mainCont.add(tableCont);

	}
    };

    /**
     * Sätter den nuvarande inloggade admin användaren
     *
     * @param loggedInUser den inloggade användaren som sätts från LoginPage
     */
    public void setCurrentAdmin(User loggedInUser) {
	currentAdmin = loggedInUser;
    }


    @Override public void switchPage(final CardSwitcher switcher, final String newPage) {
	switcher.switchTo(newPage);
    }
}

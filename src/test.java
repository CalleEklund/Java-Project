import classes.Loan;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import texthandlers.TextWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;


import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import texthandlers.TextWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class test extends JPanel
{
    private TextWriter tw;
    private HashMap<String, User> userData;
    private Gson gson;
    private FileWriter primaryWriter;
    private FileReader primaryReader;

    JFrame frame = new JFrame();
    JPanel cont = new JPanel();

    private JTextField name = new JTextField(15);
    private JTextField email = new JTextField(15);
    private JTextField password = new JTextField(15);

    private JButton add = new JButton("add user");
    private JButton logout = new JButton("logout");

    public test() throws IOException {
	primaryReader = new FileReader(new File("usersData.json"));
	gson = new GsonBuilder().setPrettyPrinting().create();
	userData = new HashMap<>();
	tw = new TextWriter();

	setLayout(new FlowLayout());
	cont.add(name);
	cont.add(email);
	cont.add(password);
	cont.add(add);
	cont.add(logout);
	Action createAcc = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {
		System.out.println(name.getText() + " " + email.getText() + " " + password.getText());
		User u = new User(name.getText(), email.getText(), password.getText());
		HashMap<String, User> temp = gson.fromJson(primaryReader, HashMap.class);
		if (temp != null){
		    temp.put(u.getUid(), u);
		    userData = temp;
		}
		userData.put(u.getUid(), u);
		try {
		    primaryWriter = new FileWriter(new File("usersData.json"));
		    primaryWriter.write(gson.toJson(userData));
		    primaryWriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}; Action out = new AbstractAction()
	{
	    @Override public void actionPerformed(ActionEvent actionEvent) {


	    }
	};
	logout.addActionListener(out);
	add.addActionListener(createAcc);

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(cont);
	frame.setSize(600, 600);
	frame.pack();
	frame.setVisible(true);
    }

    /**
     * TODO: - Radera "konton" med att en temp sträng, ta bort användare, lägg till den nya användaren.
     **/
    public static void main(String[] args) throws IOException {
	new test();


    }
}

package misc;

import classes.Loan;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextReader
{
    private File file;
    private FileReader primaryReader;
    private Gson gson;
    private ArrayList<User> userData;
    private TextWriter tw;

    public TextReader() {
	this.file = new File("src/usersData.json");
	this.userData = new ArrayList<>();
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	userData = readFromFile();
    }

    public FileReader getPrimaryReader() {
	return primaryReader;
    }

    //Läsa data från fil
    public ArrayList readFromFile() {
	try {
	    this.primaryReader = new FileReader(file);
	    this.userData = gson.fromJson(primaryReader, new TypeToken<ArrayList<User>>()
	    {
	    }.getType());
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryReader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	if (userData == null) {return new ArrayList<User>();}
	return userData;
    }

    //kolla om användare finns
    public boolean checkIfUserExists(User u) {
	for (User elem : userData) {
	    if (elem.getEmail().equals(u.getEmail()) && elem.getPassword().equals(u.getPassword())) {
		return true;
	    }
	}
	return false;
    }


//hämta specifika data från fil (användare samt lån)
    public User getUser(String key) {
	for (User elem : userData) {
	    if (elem.getEmail().equals(key)) {
		return elem;
	    }
	}
	return new User();
    }

    public void addLoanToUser(User u, Loan l) {

	HashMap<String, User> temp = new HashMap<>();

//	System.out.println(userData);
//	this.userData.entrySet().removeIf(entry -> u.getEmail().equals(entry.getKey()));
//	this.userData.put(u.getEmail(),u);

//	System.out.println(temp);

    }
}

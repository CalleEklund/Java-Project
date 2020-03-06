package texthandlers;

import classes.Loan;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class SaveData
{
    private File file;
    private FileWriter primaryWriter;
    private Gson gson;
    private FileReader primaryReader;
    private ArrayList<User> userData;

    public SaveData() {
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	this.file = new File("src/usersData.json");
	try {
	    if (!file.exists()) {file.createNewFile();}

	} catch (IOException e) {
	    e.printStackTrace();
	}
	readFromFile();

    }

    public void readFromFile() {
	try {
	    this.primaryReader = new FileReader(file);
	    this.userData = gson.fromJson(primaryReader, new TypeToken<ArrayList<User>>()
	    {
	    }.getType());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	if (userData == null) {userData = new ArrayList<User>();}
    }

    public void saveUser() {
	try {
	    this.primaryWriter = new FileWriter(file);
	    this.primaryWriter.write(gson.toJson(userData));
	    System.out.println("write success");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryWriter.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public boolean checkIfUserExists(User u) {
	readFromFile();
	for (User elem : userData) {
	    if (elem.getEmail().equals(u.getEmail()) && elem.getPassword().equals(u.getPassword())) {
		return true;
	    }
	}
	return false;
    }

    public void removeUser(User u) {
	int ind = getIndex(u);
	userData.remove(ind);
    }

    public void addNewUser(User u) {
	readFromFile();
	userData.add(u);
	saveUser();
    }

    public void saveLoan(User u, Loan l) {
	readFromFile();
	int ind = getIndex(u);
	u = userData.get(ind);
	u.addUserLoan(l);
	userData.set(ind,u);
	saveUser();
    }

    public int getIndex(User u) {
	for (int i = 0; i < userData.size(); i++) {
	    if (userData.get(i).equals(u)) {
		return i;
	    }
	}
	return -1;
    }

    public User getUser(String email, String password) {
	for (User u : userData) {
	    if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equalsIgnoreCase(password)) {
		return u;
	    }
	}
	return null;
    }

  /*  final static Loan testLoan = new Loan("test", "testdec", 1.8, 100, 100, LocalDate.now(), LocalDate.now());
    final static Loan testLoan1 = new Loan("test1", "testdec", 1.8, 100, 100, LocalDate.now(), LocalDate.now());
    final static User u1 = new User("test@gmail.com", "test");
    final static User u2 = new User("test2@gmail.com", "test2");

    public static void main(String[] args) {
	SaveData sd = new SaveData();
	sd.addNewUser(u2);
	u1.addUserLoan(testLoan1);
	sd.saveLoan(u1, testLoan1);


    }*/
}

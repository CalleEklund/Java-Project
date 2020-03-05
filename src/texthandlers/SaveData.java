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
	//this.userData.add(u);
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
	    if (elem.equals(u)) {
		return true;
	    }
	}
	return false;
    }

    public void removeUser(User u) {
	userData.removeIf(curr -> checkIfUserExists(u));
    }

    //add user to arraylist and then save
    public void addNewUser(User u){
        readFromFile();
        userData.add(u);
        saveUser();
    }
    public void saveLoan(User u, Loan l) {
	readFromFile();
        u.addUserLoan(l);
	System.out.println(userData);
	removeUser(u);
	userData.add(u);
	System.out.println(userData);
    }

    public User getUser(String email, String password) {
	for (User u : userData) {
	    if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equalsIgnoreCase(password)) {
		return u;
	    }
	}
	return null;
    }

    public static Loan testLoan = new Loan("test", "testdec", 1.8, 100, 100, LocalDate.now(), LocalDate.now());
    public static Loan testLoan1 = new Loan("test1", "testdec", 1.8, 100, 100, LocalDate.now(), LocalDate.now());
    public static User u1 = new User("test@gmail.com", "test");

    public static void main(String[] args) {
	SaveData sd = new SaveData();
//	sd.saveUser(u1);
	u1.addUserLoan(testLoan);
	sd.saveLoan(u1, testLoan1);
//	sd.removeUser(u1);
    }
}

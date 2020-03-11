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
import java.util.ArrayList;

/**
 * Sparar till textfil
 */
public class SaveData
{
    private File file;
    private FileWriter primaryWriter = null;
    private Gson gson;
    private ArrayList<User> userData;

    /**
     * Sätter sparfilen,
     * samt skapar Gson object för utskrift
     * Uppdaterar userData till alla nuvarande data från textfilen
     */
    public SaveData() {
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	this.file = new File("src/usersData.json");
	readFromFile();

    }

    /**
     * Läser all datan från textfilen och sätter data i userData variablen
     */
    public void readFromFile() {
	try {
	    final FileReader primaryReader = new FileReader(file);
	    this.userData = gson.fromJson(primaryReader, new TypeToken<ArrayList<User>>()
	    {
	    }.getType());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	if (userData == null) {userData = new ArrayList<>();}
    }

    /**
     * Spara användare till textfilen
     */
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

    /**
     * Kollar om User u finns i textfilen
     * @param u användare från User klassen
     * @return True/False om användaren finns eller inte
     */
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

    /**
     * Lägger till ny användare till userData sen spara användare till textfilen
     * @param u från User klassen
     */
    public void addNewUser(User u) {
	readFromFile();
	userData.add(u);
	saveUser();
    }

    /**
     * Sparar ett lån till användaren samt sparar till textfilen
     * @param u från User klassen
     * @param l från Loan klassen,lånet som användaren vill lägga till
     */
    public void saveLoan(User u, Loan l) {
	readFromFile();
	int ind = getIndex(u);
	u = userData.get(ind);
	u.addUserLoan(l);
	userData.set(ind,u);
	saveUser();
    }

    /**
     * @param u från User klassen
     * @return vilket index u har i textfil listan (userData)
     */
    public int getIndex(User u) {
	for (int i = 0; i < userData.size(); i++) {
	    if (userData.get(i).equals(u)) {
		return i;
	    }
	}
	return -1;
    }

    /**
     * Returnerar användare om den finns i textfilen
     * @param email från User klassen
     * @param password från User klassen
     * @return den sökta användaren från textfilen eller null om användaren inte finns
     */
    public User getUser(String email, String password) {
        readFromFile();
	for (User u : userData) {
	    if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equalsIgnoreCase(password)) {
		return u;
	    }
	}
	return null;
    }
}

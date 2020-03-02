package texthandlers;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TextReader
{
    //test för git
    private File file;
    private FileReader primaryReader;
    private Gson gson;
    private HashMap<String, User> userData;

    public TextReader() {
	this.file = new File("src/usersData.json");
	this.userData = new HashMap<>();
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	userData = readFromFile();
    }

    public FileReader getPrimaryReader() {
	return primaryReader;
    }

    //Läsa data från fil
    public HashMap readFromFile() {
	try {
	    this.primaryReader = new FileReader(file);
	    this.userData = gson.fromJson(primaryReader, HashMap.class);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryReader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return userData;
    }

    //kolla om användare finns
    public boolean checkIfUserExists(User u) {
	HashMap<String, User> currentData = readFromFile();
	if (userData != null){
	    return userData.containsKey(u.getEmail());
	}
	return false;
    }

    //hämta specifika data från fil (användare samt lån)
    public User getUser(String key) {
	return userData.get(key);
    }
}

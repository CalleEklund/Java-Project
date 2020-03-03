package texthandlers;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	if (userData != null) {
	    return userData.containsKey(u.getEmail());
	}
	return false;
    }

    //hämta specifika data från fil (användare samt lån)
    /**
     * Lägg usern i en map för att sedan hämta information
     * gör en jsonToUser funktion som returnerar en user med json datan,
     *
     * **/
    public User getUser(String key) {
	Map<String,User> user = (Map<String, User>) userData.get(key);
	System.out.println(user.get("password"));
//	User user = userData.get(key);
//	System.out.println(user);
	return new User("test","test");
    }
}

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
	try {
	    this.primaryReader = new FileReader(file);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryReader.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }

    //Läsa data från fil
    public void readFromFile() {
	userData = gson.fromJson(primaryReader, HashMap.class);
    }

    //kolla om användare finns
    public boolean checkIfUserExists(User u) {
	return userData.containsKey(u.getEmail());
    }

    //hämta specifika data från fil (användare samt lån)
    public User getUser(String key) {
	return userData.get(key);
    }
}

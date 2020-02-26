package texthandlers;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class TextWriter
{
    private File file;
    private FileWriter primaryWriter;
    private FileWriter fileCleaner;
    private HashMap<String, User> userData;
    private Gson gson;


    /**
     * TODO:
     *  - Lägg till säker skrivning
     * 	- Skulle nog kunna göra en superklass typ texthandlers
     **/
    public TextWriter() {
	this.file = new File("src/usersData.json");
	this.userData = new HashMap<>();
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	try {
	    this.primaryWriter = new FileWriter(file, true);
	    this.fileCleaner = new FileWriter(file);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryWriter.close();
		this.fileCleaner.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }

    //To hashmap, converta användare till hashmap
    public HashMap<String, User> getUserData() {
	return this.userData;
    }

    public void addUserToHashMap(User u) {
	this.userData.put(u.getEmail(), u);
    }

    public void removeUserFromHashMap(User u) {
	this.userData.remove(u.getEmail());
    }

    //appenda till fil
    public void writeToFile() {
	try {
	    primaryWriter.write(gson.toJson(userData));
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    //rensa fil
    public void cleanFile() {
	try {
	    fileCleaner.write("");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}

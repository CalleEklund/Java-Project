package texthandlers;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class TextWriter
{
    private File file;
    private FileWriter primaryWriter;
    private FileWriter fileCleaner;
    private HashMap<String, User> userData;
    private Gson gson;
    private TextReader tr;


    /**
     * TODO: - Lägg till säker skrivning - Skulle nog kunna göra en superklass typ texthandlers
     **/
    public TextWriter() {
	this.tr = new TextReader();
	this.file = new File("src/usersData.json");
	this.userData = new HashMap<>();
	this.gson = new GsonBuilder().setPrettyPrinting().create();

    }

    //To hashmap, converta användare till hashmap
    public HashMap<String, User> getUserData() {
	return this.userData;
    }

    public void addUserToHashMap(User u) {
	HashMap<String, User> temp = tr.readFromFile();
	if (temp != null) {
	    temp.put(u.getEmail(), u);
	    this.userData = temp;
	}
	this.userData.put(u.getEmail(), u);
    }

    public void removeUserFromHashMap(User u) {
	this.userData.remove(u.getEmail());
    }

    //appenda till fil
    public void writeToFile() {


	try {
	    this.primaryWriter = new FileWriter(file);
	    primaryWriter.write(gson.toJson(userData));
	     System.out.println("write success");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.primaryWriter.close();
		System.out.println("stream closed");
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }

    //rensa fil
    public void cleanFile() {
	try {
	    this.fileCleaner = new FileWriter(file);
	    fileCleaner.write("");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		this.fileCleaner.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

}

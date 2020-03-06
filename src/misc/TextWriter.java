package misc;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import misc.TextReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TextWriter
{
    private File file;
    private FileWriter primaryWriter;
    private FileWriter fileCleaner;
    private HashMap<String, User> userData;
    private Gson gson;
    private TextReader tr;

    private ArrayList<User> test;
    private User currentUser;

    /**
     * TODO: - Lägg till säker skrivning - Skulle nog kunna göra en superklass typ texthandlers
     **/
    public TextWriter() {
	this.tr = new TextReader();
	this.file = new File("src/usersData.json");
	this.userData = new HashMap<>();
	this.gson = new GsonBuilder().setPrettyPrinting().create();

	this.test = tr.readFromFile();
	this.currentUser = null;

    }

    //To hashmap, converta användare till hashmap
    public HashMap<String, User> getUserData() {
	return this.userData;
    }

    public void addUserToHashMap(User u) {
	this.test.add(u);

    }

    public void setCurrentUser(User u) {
	this.currentUser = u;

    }

    public void removeUserFromHashMap(User u) {
	this.userData.remove(u.getEmail());
    }

    //appenda till fil
    public void writeToFile() {

	test.add(currentUser);
	try {
	    this.primaryWriter = new FileWriter(file);
	    primaryWriter.write(gson.toJson(test));
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

    public void removeUser(User u) {
	ArrayList<User> temp = tr.readFromFile();
//	System.out.println("data" + temp);
	temp.remove(u);
	System.out.println("data" + temp);
	for (User elem : temp) {
	    this.currentUser = elem;
	    writeToFile();
	}
    }

    public void updateData(User u) {
//	System.out.println(test.size());

	Iterator<User> itr = test.iterator();
	while (itr.hasNext()) {
	    User user = itr.next();
	    if (tr.checkIfUserExists(user)) {
		    test.remove(user);
	    }

	}
//	if (tr.checkIfUserExists(u)) {
//	    test.remove(u);
//	}
	test.add(u);
//	System.out.println(test);
//	System.out.println(test.size());
	writeToFile();
//	User currU = tr.getUser(u.getEmail());
//	ArrayList<User> data = tr.readFromFile();
//	System.out.println(data);
//	this.currentUser = u;
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

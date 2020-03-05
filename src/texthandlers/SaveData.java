package texthandlers;

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

public class SaveData
{
    private File file;
    private FileWriter primaryWriter;
    private Gson gson;
    private FileReader primaryReader;

    private User currentUser;
    private ArrayList<User> userData;

    public SaveData() {
	this.gson = new GsonBuilder().setPrettyPrinting().create();
	this.file = new File("src/usersData.json");
	try {
	    if (!file.exists()) {file.createNewFile();}

	} catch (IOException e) {
	    e.printStackTrace();
	}
	this.currentUser = null;
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

    public void saveUser(User u) {
	this.userData.add(u);
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
	for (User elem : userData) {
	    if (elem.getEmail().equals(u.getEmail()) && elem.getPassword().equals(u.getPassword())) {
		return true;
	    }
	}
	return false;
    }

    public void removeUser(User u) {
	System.out.println("user" + u.getEmail());
	for (int i = 0; i < userData.size(); i++) {
	    if(checkIfUserExists(userData.get(i))){
	        userData.remove(i);
	    }
	}
    }

    public static User tu = new User("email1", "test1");

    public static void main(String[] args) {
	SaveData sd = new SaveData();
	//System.out.println(sd.checkIfUserExists(tu));
	//sd.saveUser(tu);
	//System.out.println(sd.userData);
	sd.removeUser(tu);
	//System.out.println(sd.userData);
    }
}

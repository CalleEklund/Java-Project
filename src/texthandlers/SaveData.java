package texthandlers;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

	this.currentUser = null;

    }
}

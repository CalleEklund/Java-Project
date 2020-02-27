import classes.Loan;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;


public class test
{
    /**
     * TODO:
     *  - Radera "konton" med att en temp sträng, ta bort användare, lägg till den nya användaren.
     *
     * **/
    public static void main(String[] args) throws FileNotFoundException {
	HashMap<String, User> data = new HashMap<>();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();


	User u1 = new User("Calle", "test1@gmail.com", "losen");
	User u2 = new User("Tyra", "test2@gmail.com", "losen");
	u1.addUserLoanshm(new Loan("test1","testdesc1testdesc1testdesc1",2, 1000, 100, LocalDate.of(2000, 10, 10), LocalDate.now()));
	u2.addUserLoanshm(new Loan("test2", "testdesc2", 2, 1000, 100, LocalDate.of(2000, 10, 10), LocalDate.now()));

	data.put(u1.getEmail(),u1);
	data.put(u2.getEmail(),u2);
	System.out.println(gson.toJson(data));


	File file = new File("src/usersData.json");
	FileWriter fw = null;
	FileReader fr = new FileReader(file);
	FileWriter fclean = null;
//	try {
//	    fr = new FileReader(file);
//	    fw = new FileWriter(file, true);
//	    //rensa filen
//	    fclean = new FileWriter(file);
////	    fclean.write("");
//	    fw.write(gson.toJson(data));
//
//	} catch (IOException e) {
//
//	} finally {
//	    try {
//		assert fw != null;
//		fw.close();
//	    } catch (IOException e) {
//		e.printStackTrace();
//	    }
//	}


	HashMap outdata = gson.fromJson(fr, HashMap.class);

	System.out.println("OUT"+gson.toJson(outdata));




    }
}

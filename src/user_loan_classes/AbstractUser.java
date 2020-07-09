package user_loan_classes;


/**
 * Abstrakta klassen för User klassen som används eftersom varje användare måste ha en email samt lösenord. Även olika
 * konstruktorer eftersom en användare kan hämtas/skapas på olika sätt samt en tom konstruktor.
 */
public abstract class AbstractUser
{
    protected String email = null;
    protected String password = null;
    protected String name = null;

    protected AbstractUser(final String emailDB, final String passwordDB, final String nameDB) {
	this.email = emailDB;
	this.password = passwordDB;
	this.name = nameDB;

    }

    protected AbstractUser() {
    }


    /**
     * Getter för Email och lösenord.
     */
    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }

    public String getName() {
	return name;
    }
}


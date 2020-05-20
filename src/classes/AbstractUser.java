package classes;

public abstract class AbstractUser
{
    protected String email = null;
    protected String password = null;

    protected AbstractUser(final String uid, final String email, final String password) {
	this.email = email;
	this.password = password;
    }
    protected AbstractUser(final String emailDB, final String passwordDB) {
	this.email = emailDB;
	this.password = passwordDB;

    }
    protected AbstractUser() {
    }

    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }
}


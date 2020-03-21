package classes;

import java.util.UUID;

public abstract class AbstractUser
{
    protected String uid;
    protected String email;
    protected String password;

    public AbstractUser(final String uid, final String email, final String password) {
	this.uid =  UUID.randomUUID().toString();
	this.email = email;
	this.password = password;
    }
    public AbstractUser(final String emailDB, final String passwordDB) {
	this.email = emailDB;
	this.password = passwordDB;

    }
    public AbstractUser() {
    }


}


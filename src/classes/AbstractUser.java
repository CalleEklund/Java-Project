package classes;

import java.util.UUID;

public abstract class AbstractUser
{
    protected String uid = null;
    protected String email = null;
    protected String password = null;

    protected AbstractUser(final String uid, final String email, final String password) {
	this.uid =  UUID.randomUUID().toString();
	this.email = email;
	this.password = password;
    }
    protected AbstractUser(final String emailDB, final String passwordDB) {
	this.email = emailDB;
	this.password = passwordDB;

    }
    protected AbstractUser() {
    }


}


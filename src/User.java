import java.util.UUID;

public class User {
    private String uid,name, email, password;

    public User(String name, String email, String password) {
        this.uid = UUID.randomUUID().toString();;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "uid=" + uid +", name='" + name + '\'' +", email='" + email + '\'' +", password='" + password;
    }

    public static void main(String[] args) {
        User test = new User("calle","carek123@student.liu.se","losen123");
        System.out.println(test);
    }
}

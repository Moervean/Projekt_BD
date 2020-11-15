package Model;



import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "users")
public class User  {
    @DatabaseField(id = true)
    private int ID;
    @DatabaseField
    private String password;
    @DatabaseField
    private String name;
    @DatabaseField
    private String secondName;
    @DatabaseField
    private String mail;
    @DatabaseField
    private String phoneNumber;
    @DatabaseField
    private String priv;
    @DatabaseField
    private int Coach_ID;

    public User(int ID, String password, String name, String secondName, String mail, String phoneNumber, String priv, int coach_ID) {
        this.ID = ID;
        this.password = password;
        this.name = name;
        this.secondName = secondName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.priv = priv;
        Coach_ID = coach_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public int getCoach_ID() {
        return Coach_ID;
    }

    public void setCoach_ID(int coach_ID) {
        Coach_ID = coach_ID;
    }

    public User() {
    }
}

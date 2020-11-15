package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Coaches")
public class Coach {
    @DatabaseField(id=true)
    private int ID_coach;
    @DatabaseField
    private String pass;
    @DatabaseField
    private String name;
    @DatabaseField
    private String secondName;
    @DatabaseField
    private int price;
    @DatabaseField
    private int salary;
    @DatabaseField
    private String phoneNumber;
    @DatabaseField
    private String mail;

    public int getID_coach() {
        return ID_coach;
    }

    public void setID_coach(int ID_coach) {
        this.ID_coach = ID_coach;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Coach(int ID_coach, String pass, String name, String secondName, int price, int salary, String phoneNumber, String mail) {
        this.ID_coach = ID_coach;
        this.pass = pass;
        this.name = name;
        this.secondName = secondName;
        this.price = price;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public Coach() {
    }
}

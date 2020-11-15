package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Messages")
public class Message {
    @DatabaseField(id = true)
    private int ID_message;
    @DatabaseField
    private int Coach_ID;
    @DatabaseField
    private String content;
    @DatabaseField
    private String date;
    @DatabaseField
    private String user_mail;

    public int getID_message() {
        return ID_message;
    }

    public void setID_message(int ID_message) {
        this.ID_message = ID_message;
    }

    public int getCoach_ID() {
        return Coach_ID;
    }

    public void setCoach_ID(int coach_ID) {
        Coach_ID = coach_ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public Message(int ID_message, int coach_ID, String content, String date, String user_mail) {
        this.ID_message = ID_message;
        Coach_ID = coach_ID;
        this.content = content;
        this.date = date;
        this.user_mail = user_mail;
    }

    public Message() {
    }
}

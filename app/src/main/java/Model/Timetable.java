package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Timetables")
public class Timetable {
    @DatabaseField
    private String date;
    @DatabaseField
    private int user_ID;
    @DatabaseField
    private int workout_ID;


    public Timetable(String date, int user_ID, int workout_ID) {
        this.date = date;
        this.user_ID = user_ID;
        this.workout_ID = workout_ID;
    }

    public Timetable() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getWorkout_ID() {
        return workout_ID;
    }

    public void setWorkout_ID(int workout_ID) {
        this.workout_ID = workout_ID;
    }
}

package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Workout_Exercise")
public class Workout_Exercise {
    @DatabaseField
    private int Workout_ID;
    @DatabaseField
    private int Exericse_ID;

    public int getWorkout_ID() {
        return Workout_ID;
    }

    public void setWorkout_ID(int workout_ID) {
        Workout_ID = workout_ID;
    }

    public int getExericse_ID() {
        return Exericse_ID;
    }

    public void setExericse_ID(int exericse_ID) {
        Exericse_ID = exericse_ID;
    }

    public Workout_Exercise() {
    }

    public Workout_Exercise(int workout_ID, int exericse_ID) {
        Workout_ID = workout_ID;
        Exericse_ID = exericse_ID;
    }
}

package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Exercises")
public class Exercise {
    @DatabaseField(id = true)
    private int ID_exercise;
    @DatabaseField
    private String name;
    @DatabaseField
    private String muscle;
    @DatabaseField
    private int sets;
    @DatabaseField
    private int reps;


    public int getID_exercise() {
        return ID_exercise;
    }

    public void setID_exercise(int ID_exercise) {
        this.ID_exercise = ID_exercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Exercise(int ID_exercise, String name, String muscle, int sets, int reps) {
        this.ID_exercise = ID_exercise;
        this.name = name;
        this.muscle = muscle;
        this.sets = sets;
        this.reps = reps;
    }

    public Exercise() {
    }
}

package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "workouts")
public class Workout {
    @DatabaseField(id = true)
    private int ID_workout;
    @DatabaseField
    private String name;
    @DatabaseField
    private String category;

    public int getID_workout() {
        return ID_workout;
    }

    public void setID_workout(int ID_workout) {
        this.ID_workout = ID_workout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Workout(int ID_workout, String name, String category) {
        this.ID_workout = ID_workout;
        this.name = name;
        this.category = category;
    }

    public Workout() {
    }
}

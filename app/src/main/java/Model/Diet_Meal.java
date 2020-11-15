package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Diet_Meal")
public class Diet_Meal {
    @DatabaseField
    private int Diet_ID;
    @DatabaseField
    private int Meal_ID;

    public int getDiet_ID() {
        return Diet_ID;
    }

    public void setDiet_ID(int diet_ID) {
        Diet_ID = diet_ID;
    }

    public int getMeal_ID() {
        return Meal_ID;
    }

    public void setMeal_ID(int meal_ID) {
        Meal_ID = meal_ID;
    }

    public Diet_Meal() {
    }

    public Diet_Meal(int diet_ID, int meal_ID) {
        Diet_ID = diet_ID;
        Meal_ID = meal_ID;
    }
}

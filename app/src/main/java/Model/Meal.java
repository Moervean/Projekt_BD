package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Meals")
public class Meal {
    @DatabaseField(id = true)
    private int ID_meal;
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private int kcal;
    @DatabaseField
    private boolean nuts_Allergy;
    @DatabaseField
    private boolean lactose_Allergy;

    public int getID_meal() {
        return ID_meal;
    }

    public void setID_meal(int ID_meal) {
        this.ID_meal = ID_meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public boolean isNuts_Allergy() {
        return nuts_Allergy;
    }

    public void setNuts_Allergy(boolean nuts_Allergy) {
        this.nuts_Allergy = nuts_Allergy;
    }

    public boolean isLactose_Allergy() {
        return lactose_Allergy;
    }

    public void setLactose_Allergy(boolean lactose_Allergy) {
        this.lactose_Allergy = lactose_Allergy;
    }

    public Meal(int ID_meal, String name, String type, int kcal, boolean nuts_Allergy, boolean lactose_Allergy) {
        this.ID_meal = ID_meal;
        this.name = name;
        this.type = type;
        this.kcal = kcal;
        this.nuts_Allergy = nuts_Allergy;
        this.lactose_Allergy = lactose_Allergy;
    }

    public Meal() {
    }
}

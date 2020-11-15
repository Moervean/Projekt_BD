package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Diets")
public class Diet {
    @DatabaseField(id = true)
    private int ID_diet;
    @DatabaseField
    private String name;
    @DatabaseField
    private String category;
    @DatabaseField
    private Integer Protege_ID;

    public Diet(int ID_diet, String name, String category, Integer protege_ID) {
        this.ID_diet = ID_diet;
        this.name = name;
        this.category = category;
        Protege_ID = protege_ID;
    }

    public Diet() {
    }

    public int getID_diet() {
        return ID_diet;
    }

    public void setID_diet(int ID_diet) {
        this.ID_diet = ID_diet;
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

    public int getProtege_ID() {
        return Protege_ID;
    }

    public void setProtege_ID(int protege_ID) {
        Protege_ID = protege_ID;
    }


}

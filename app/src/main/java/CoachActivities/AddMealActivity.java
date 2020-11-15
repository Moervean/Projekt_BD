package CoachActivities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import Data.ConnectionClass;
import Model.Meal;

public class AddMealActivity extends AppCompatActivity {
    private EditText mealDesc;
    private Button addMeal;
    private EditText kcal;
    private Spinner type;
    private CheckBox nutsAllergy;
    private CheckBox lactoseAllergy;
    private static final String KEY_DIET_ID = "Diet_ID";
    private int value;

    private String[] meals = {"śniadanie","II śniadanie","obiad","podwieczorek","kolacja"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mealDesc = (EditText) findViewById(R.id.mealDescription);
        addMeal = (Button)findViewById(R.id.uploadMeal);
        kcal = (EditText)findViewById(R.id.kcalTV);
        type = (Spinner) findViewById(R.id.mealType);
        nutsAllergy = (CheckBox)findViewById(R.id.nutsAllergyCB);
        lactoseAllergy = (CheckBox)findViewById(R.id.lactoseAllergyCB);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMealActivity.this,R.layout.spinner_layout,meals);
        type.setAdapter(adapter);


        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(mealDesc.getText().toString()) ||
                !TextUtils.isEmpty(kcal.getText().toString())) {
                    String mealDescription = mealDesc.getText().toString();
                    int calories = Integer.parseInt(kcal.getText().toString());
                    String typeS = type.getSelectedItem().toString();
                    boolean nuts = nutsAllergy.isChecked();
                    boolean lactose = lactoseAllergy.isChecked();
                    SaveMeal saveMeal = new SaveMeal(mealDescription, calories, typeS, nuts, lactose);
                    saveMeal.execute();
                }else{
                    Toast.makeText(AddMealActivity.this,getResources().getString(R.string.blankMealWarn),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public class SaveMeal extends AsyncTask<String,String,String>{

        private String mealDescription;
        private int calories;
        private String type;
        private boolean nutsAllergy;
        private boolean lactoseAllergy;

        public SaveMeal(String mealDescription, int calories, String type, boolean nutsAllergy, boolean lactoseAllergy) {
            this.mealDescription = mealDescription;
            this.calories = calories;
            this.type = type;
            this.nutsAllergy = nutsAllergy;
            this.lactoseAllergy = lactoseAllergy;
        }

        @Override
        protected String doInBackground(String... strings) {
            if(!TextUtils.isEmpty(mealDescription)){

                ConnectionSource connectionSource = null;
                try {

                    connectionSource = new ConnectionClass().buildConnect();
                    Dao<Meal,String> mealDao = DaoManager.createDao(connectionSource,Meal.class);
                    int numRows =  (int) mealDao.countOf();
                    Meal meal = new Meal(numRows,mealDescription,type,calories,nutsAllergy,lactoseAllergy);
                    TableUtils.createTableIfNotExists(connectionSource,Meal.class);
                    mealDao.create(meal);
                } catch (SQLException e) {
                    e.printStackTrace();
                }




            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddMealActivity.this,getResources().getString(R.string.blankDietError),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return null;
        }
    }
}

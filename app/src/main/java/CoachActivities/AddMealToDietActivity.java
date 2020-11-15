package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.AddExerciseToWorkoutRecyclerViewAdapter;
import Model.AddMealToDIetRecyclerViewAdapter;
import Model.Diet;
import Model.Diet_Meal;
import Model.Exercise;
import Model.Meal;
import Model.Workout_Exercise;

public class AddMealToDietActivity extends AppCompatActivity {
    private RecyclerView mealsList;
    private int value;
    private static final String KEY_DIET_ID = "Diet_ID";
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_to_diet);

        value = getIntent().getIntExtra(KEY_DIET_ID, 0);
        mealsList = (RecyclerView) findViewById(R.id.addMealToDietRV);
        addMeal = (FloatingActionButton) findViewById(R.id.addMealFloatingButt);
        mealsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mealsList.setLayoutManager(layoutManager);
        LoadMeals loadMeals = new LoadMeals();
        loadMeals.execute();
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddMealToDietActivity.this,AddMealActivity.class));
            }
        });
    }

    private class LoadMeals extends AsyncTask<Void, Void, Void> {
        private List<Meal> list;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Meal, String> mealsDao = DaoManager.createDao(connectionSource, Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Diet_Meal.class);
                list = new ArrayList<>();
                for (Meal m : mealsDao) {
                    list.add(m);
                }
                Dao<Diet_Meal, String> weDao = DaoManager.createDao(connectionSource, Diet_Meal.class);
                QueryBuilder<Diet_Meal, String> builder = weDao.queryBuilder();
                builder.where().eq("Diet_ID", value);
                List<Diet_Meal> DMList = weDao.query(builder.prepare());
                for(int i = 0;i<DMList.size();i++){

                }

                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < DMList.size(); j++) {
                        if (list.get(i).getID_meal() == DMList.get(j).getMeal_ID()) {

                            list.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                connectionSource.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mealsList.setAdapter(new AddMealToDIetRecyclerViewAdapter(AddMealToDietActivity.this, value, list));
                    }
                });
            } catch (SQLException | IOException e) {

            }
            return null;
        }
    }
}
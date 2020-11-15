package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.Diet_Meal;
import Model.EditDietRecyclerViewAdapter;
import Model.EditWorkoutRecyclerViewAdapter;
import Model.Meal;

public class EditDietActivity extends AppCompatActivity {
    private RecyclerView mealsList;
    private List<Meal> meals;
    private FloatingActionButton addMeal;
    private RecyclerView.LayoutManager layoutManager;
    private int value;
    private static final String KEY_DIET_ID = "Diet_ID";

    @Override
    protected void onResume() {
        super.onResume();
        LoadMeals loadMeals = new LoadMeals();
        loadMeals.execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diet);

        mealsList = (RecyclerView)findViewById(R.id.editDietRV);
        addMeal = (FloatingActionButton)findViewById(R.id.editDietFloating);

        mealsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mealsList.setLayoutManager(layoutManager);
        value = getIntent().getIntExtra(KEY_DIET_ID,0);

        LoadMeals loadMeals = new LoadMeals();
        loadMeals.execute();

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (EditDietActivity.this,AddMealToDietActivity.class);
                intent.putExtra(KEY_DIET_ID,value);
                startActivity(intent);
            }
        });
    }

    private class LoadMeals extends AsyncTask<Void,Void,Void> {
        private List<Meal> mealList;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                mealList=new ArrayList<>();
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Diet_Meal,String> mealsDao = DaoManager.createDao(connectionSource,Diet_Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Diet_Meal.class);
                QueryBuilder<Diet_Meal,String> builder = mealsDao.queryBuilder();
                builder.where().eq("Diet_ID",value);

                List<Diet_Meal> DMlist = mealsDao.query(builder.prepare());
                Dao<Meal,String> mealDao = DaoManager.createDao(connectionSource,Meal.class);
                QueryBuilder<Meal,String> builderMD = mealDao.queryBuilder();
                for(Diet_Meal dm : DMlist){
                    builderMD.where().eq("ID_meal",dm.getMeal_ID());
                    List<Meal> meal = mealDao.query(builderMD.prepare());

                    try {
                        mealList.add(meal.get(0));
                    }catch (Exception e){

                    }
                    meal.clear();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mealsList.setAdapter(new EditDietRecyclerViewAdapter(mealList,EditDietActivity.this));
                    }
                });

            }catch (SQLException e){

            }
            return null;
        }
    }
}
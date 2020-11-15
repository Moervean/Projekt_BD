package UserActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.Diet;
import Model.Diet_Meal;
import Model.Meal;
import UserActivitesModel.ShowDietRecyclerViewAdapter;

public class ShowDietActivity extends AppCompatActivity {

    private RecyclerView dietList;
    private RecyclerView.LayoutManager layoutManager;
    private static final String KEY_ID = "ID";
    private int user_ID;
    private List<Meal> mealList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diet);

        dietList = (RecyclerView)findViewById(R.id.protegeDietList);
        user_ID = getIntent().getIntExtra(KEY_ID,0);
        layoutManager = new LinearLayoutManager(this);
        dietList.setLayoutManager(layoutManager);
        dietList.setHasFixedSize(true);

        LoadMeals loadMeals = new LoadMeals();
        loadMeals.execute();

    }

    private class LoadMeals extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                mealList = new ArrayList<>();
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Diet,String> dietFind = DaoManager.createDao(connectionSource,Diet.class);
                TableUtils.createTableIfNotExists(connectionSource,Diet.class);
                QueryBuilder<Diet,String> dietBuilder = dietFind.queryBuilder();
                dietBuilder.where().eq("Protege_ID",user_ID);
                QueryBuilder<Diet,String> dietBuilder2 = dietFind.queryBuilder();


                List<Diet> diets = dietFind.query(dietBuilder.prepare());

                dietBuilder2.where().eq("category",diets.get(0).getCategory())
                        .and().eq("name",diets.get(0).getName())
                        .and().isNull("Protege_ID");
                diets.clear();
                diets = dietFind.query(dietBuilder2.prepare());

                Dao<Diet_Meal,String> DMDao = DaoManager.createDao(connectionSource,Diet_Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Diet_Meal.class);
                QueryBuilder<Diet_Meal,String> dmBuilder = DMDao.queryBuilder();
                dmBuilder.where().eq("Diet_ID",diets.get(0).getID_diet());
                List<Diet_Meal> DMList = DMDao.query(dmBuilder.prepare());
                Dao<Meal,String> mealFind = DaoManager.createDao(connectionSource,Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Meal.class);
                QueryBuilder<Meal,String> mealsBuilder = mealFind.queryBuilder();
                for(Diet_Meal dm : DMList){
                    mealsBuilder.where().eq("ID_meal",dm.getMeal_ID());
                    List<Meal> meal = mealFind.query(mealsBuilder.prepare());
                    Log.d("MEALMEAL",meal.get(0).getName());
                    mealList.add(meal.get(0));
                    meal.clear();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dietList.setAdapter(new ShowDietRecyclerViewAdapter(ShowDietActivity.this, mealList));
                    }
                });


            }catch (SQLException e){

            }
            return null;
        }
    }
}
package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.Diet;
import Model.DietsRecyclerViewAdapter;
import Model.WorkoutDialog;

public class DietListActivity extends AppCompatActivity implements WorkoutDialog.WorkoutDialogListener {

    private RecyclerView dietList;
    private FloatingActionButton addDietButton;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_list);

        dietList = (RecyclerView)findViewById (R.id.dietList);
        addDietButton = (FloatingActionButton)findViewById(R.id.addDietFloating);
        ctx = DietListActivity.this;

        layoutManager = new LinearLayoutManager(this);
        dietList.setHasFixedSize(true);
        dietList.setLayoutManager(layoutManager);
        LoadDiets loadDiets = new LoadDiets();
        loadDiets.execute();

        addDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDialog workoutDialog = new WorkoutDialog();
                workoutDialog.show(getSupportFragmentManager(),"Diet dialog");
            }
        });

    }
    @Override
    public void getTexts(String name,String category){
        CreateDiet createDiet = new CreateDiet(name,category);
        createDiet.execute();
    }

    private class LoadDiets extends AsyncTask<Void,Void,Void> {
        private List<Diet> diets;
        @Override
        protected Void doInBackground(Void... voids) {

            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                TableUtils.createTableIfNotExists(connectionSource,Diet.class);
                Dao<Diet,String> dietDao = DaoManager.createDao(connectionSource,Diet.class);
                QueryBuilder<Diet,String> builder = dietDao.queryBuilder();
                builder.where().isNull("Protege_ID");
                diets = dietDao.query(builder.prepare());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dietList.setAdapter(new DietsRecyclerViewAdapter(diets,ctx));
                    }
                });
            }catch (SQLException e){

            }
            return null;
        }
    }

    private class CreateDiet extends AsyncTask<Void,Void,Void> {
        private String name;
        private LoadDiets loadDiets = new LoadDiets();

        public CreateDiet(String name, String category) {
            this.name = name;
            this.category = category;
        }

        private String category;


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                TableUtils.createTableIfNotExists(connectionSource,Diet.class);
                Dao<Diet,String> dietsDao = DaoManager.createDao(connectionSource,Diet.class);
                QueryBuilder<Diet,String> builder = dietsDao.queryBuilder();
                builder.where().eq("category",category)
                        .and().eq("name",name);
                List<Diet> diets = dietsDao.query(builder.prepare());
                if(diets.size()>0) {
                    int rowNumb = (int) dietsDao.countOf();
                    Diet diet = new Diet(rowNumb, name, category, null);
                    dietsDao.create(diet);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadDiets.execute();
                        }
                    });
                }else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DietListActivity.this,getResources().getString(R.string.diet_extists_error),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                connectionSource.close();

            }catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.AddDietToProtegeRecyclerViewAdapter;
import Model.Diet;
import Model.DietsRecyclerViewAdapter;

public class AddDietToProtegeActivity extends AppCompatActivity {

    private RecyclerView dietList;
    private FloatingActionButton addDietButton;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx;
    private int value;
    private static final String KEY_USER_ID = "User_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_to_protege);

        dietList = (RecyclerView)findViewById(R.id.add_dietRV);
        ctx = AddDietToProtegeActivity.this;
        value = getIntent().getIntExtra(KEY_USER_ID,0);
        layoutManager = new LinearLayoutManager(this);
        dietList.setHasFixedSize(true);
        dietList.setLayoutManager(layoutManager);

        LoadDiets loadDiets = new LoadDiets();
        loadDiets.execute();
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
                        dietList.setAdapter(new AddDietToProtegeRecyclerViewAdapter(diets,ctx,value));
                    }
                });
            }catch (SQLException e){

            }
            return null;
        }
    }
}
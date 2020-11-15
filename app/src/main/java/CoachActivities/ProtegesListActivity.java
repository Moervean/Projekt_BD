package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.projekt_bd.R;
import com.google.android.material.tabs.TabLayout;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.ProtegeListRecyclerViewAdapter;
import Model.User;

public class ProtegesListActivity extends AppCompatActivity {

    private RecyclerView protegesList;
    private RecyclerView.LayoutManager layoutManager;
    private int value;
    private Context ctx;
    private List<User> protegeList;
    private static final String KEY_COACH_ID = "coach_ID";
    private static final String KEY_USER_MAIL = "User_Mail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proteges_list);

        protegesList = (RecyclerView)findViewById(R.id.protegesListRV);
        protegesList.setHasFixedSize(true);
        ctx = ProtegesListActivity.this;
        layoutManager = new LinearLayoutManager(this);
        protegesList.setLayoutManager(layoutManager);
        value = getIntent().getIntExtra(KEY_COACH_ID,0);
        LoadProteges loadProteges = new LoadProteges();
        loadProteges.execute();
    }

    private class LoadProteges extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<User,String> protegeDao = DaoManager.createDao(connectionSource,User.class);
                TableUtils.createTableIfNotExists(connectionSource,User.class);
                QueryBuilder<User,String> builder = protegeDao.queryBuilder();
                builder.where().eq("Coach_ID",value);
                protegeList = protegeDao.query(builder.prepare());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        protegesList.setAdapter(new ProtegeListRecyclerViewAdapter(protegeList,ctx,value));
                    }
                });

            }catch (SQLException e){

            }
            return null;
        }
    }
}
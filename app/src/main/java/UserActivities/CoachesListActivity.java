package UserActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.Coach;
import Model.CoachesListRecyclerViewAdapter;
import Model.User;

public class CoachesListActivity extends AppCompatActivity {

    private TextView mainCoachName;
    private  TextView mainCoachSecondName;
    private TextView mainCoachEmail;
    private TextView mainCoachPhoneNumber;
    private RecyclerView coachesList;
    private RecyclerView.LayoutManager layoutManager;
    private static final String KEY_ID = "ID";
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_list2);

        mainCoachName = (TextView)findViewById(R.id.mainCoachName);
        mainCoachSecondName = (TextView)findViewById(R.id.mainCoachSecondName);
        mainCoachEmail = (TextView)findViewById(R.id.mainCoachEmail);
        mainCoachPhoneNumber = (TextView)findViewById(R.id.mainCoachPhoneNumber);
        coachesList = (RecyclerView)findViewById(R.id.otherCoachesRV);
        layoutManager = new LinearLayoutManager(this);
        value = getIntent().getIntExtra(KEY_ID,0);

        coachesList.setLayoutManager(layoutManager);
        coachesList.setHasFixedSize(true);

        LoadCoaches loadCoaches = new LoadCoaches();
        loadCoaches.execute();

    }

    private class LoadCoaches extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<User,String> user = DaoManager.createDao(connectionSource,User.class);
                QueryBuilder<User,String> builderUser = user.queryBuilder();
                builderUser.where().eq("ID",value);
                List<User> users = user.query(builderUser.prepare());

                Dao<Coach,String> coachesDao = DaoManager.createDao(connectionSource,Coach.class);
                final List<Coach> coaches = coachesDao.queryForAll();
                for(final Coach c : coaches){
                    if(c.getID_coach() == users.get(0).getCoach_ID()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainCoachName.setText(c.getName());
                                mainCoachSecondName.setText(c.getSecondName());
                                mainCoachEmail.setText(c.getMail());
                                mainCoachPhoneNumber.setText(c.getPhoneNumber());
                            }
                        });
                        coaches.remove(c);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        coachesList.setAdapter(new CoachesListRecyclerViewAdapter(coaches));
                    }
                });
            }catch (SQLException e){

            }
            return null;
        }
    }
}
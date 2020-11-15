package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import Data.ConnectionClass;
import Model.Coach;
import Model.Message;
import Model.PostsRecyclerViewAdapter;

public class CoachProfileActivity extends AppCompatActivity {
    private TextView coachName;
    private TextView coachSecondName;
    private TextView coachEmail;
    private TextView coachPhoneNumber;
    private RecyclerView postsList;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addPost;
    private static final String KEY_COACH_ID = "coach_ID";
    private int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        value = getIntent().getIntExtra(KEY_COACH_ID,0);
        layoutManager = new LinearLayoutManager(this);

        coachName = (TextView)findViewById(R.id.coach_profile_name);
        coachSecondName = (TextView)findViewById(R.id.coach_profile_secondName);
        coachEmail = (TextView)findViewById(R.id.coach_profile_email);
        coachPhoneNumber = (TextView)findViewById(R.id.coach_profile_phoneNumber);
        addPost = (FloatingActionButton)findViewById(R.id.add_post);
        postsList = (RecyclerView)findViewById(R.id.posts_RecyclerView);
        postsList.setHasFixedSize(true);
        postsList.setLayoutManager(layoutManager);

        FindCoach findCoach = new FindCoach();
        findCoach.execute();

        FindPosts findPosts = new FindPosts();
        findPosts.execute();

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachProfileActivity.this,AddPostActivity.class);
                intent.putExtra(KEY_COACH_ID,value);
                startActivity(intent);
            }
        });
    }

    private class FindCoach extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Coach,String> coachDao = DaoManager.createDao(connectionSource,Coach.class);
                QueryBuilder<Coach,String> builder = coachDao.queryBuilder();
                builder.where().eq("ID_coach",value);
                final List<Coach> coach =  coachDao.query(builder.prepare());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        coachName.setText(coach.get(0).getName());
                        coachSecondName.setText(coach.get(0).getSecondName());
                        coachEmail.setText(coach.get(0).getMail());
                        coachPhoneNumber.setText(coach.get(0).getPhoneNumber());

                    }
                });
                connectionSource.close();

            }catch(Exception e){

            }
            return null;
        }
    }

    private class FindPosts extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Message,String> messagesDao = DaoManager.createDao(connectionSource,Message.class);
                QueryBuilder<Message,String> builder = messagesDao.queryBuilder();
                TableUtils.createTableIfNotExists(connectionSource,Message.class);
                builder.where().eq("Coach_ID",value).and().isNull("user_mail");
                final List<Message> messages = messagesDao.query(builder.prepare());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postsList.setAdapter(new PostsRecyclerViewAdapter(messages));
                    }
                });

                connectionSource.close();

            }catch(Exception e){

            }
            return null;
        }
    }
}
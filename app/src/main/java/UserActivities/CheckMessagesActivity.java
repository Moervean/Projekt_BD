package UserActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.PostsRecyclerViewAdapter;
import Model.Message;

public class CheckMessagesActivity extends AppCompatActivity {

    private RecyclerView posts;
    private RecyclerView.LayoutManager layoutManager;
    private String mail;
    private static final String KEY_MAIL = "MAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_messages);

        posts = (RecyclerView)findViewById(R.id.user_messagesRV);
        posts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        posts.setLayoutManager(layoutManager);

        mail=getIntent().getStringExtra(KEY_MAIL);

        LoadMessages loadMessages = new LoadMessages();
        loadMessages.execute();
    }

    private class LoadMessages extends AsyncTask<Void,Void,Void> {
        private List<Message> messageList;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Message,String> messagesDao = DaoManager.createDao(connectionSource,Message.class);
                QueryBuilder<Message,String> builder = messagesDao.queryBuilder();
                builder.where().eq("user_mail",mail);
                messageList = messagesDao.query(builder.prepare());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        posts.setAdapter(new PostsRecyclerViewAdapter(messageList));
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
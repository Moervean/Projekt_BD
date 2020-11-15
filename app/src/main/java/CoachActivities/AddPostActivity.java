package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Data.ConnectionClass;
import Model.Message;

public class AddPostActivity extends AppCompatActivity {
    private static final String KEY_COACH_ID = "coach_ID";
    private EditText messageContent;
    private Button sendMessage;
    private int coach_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        messageContent = (EditText )findViewById(R.id.messageContent);
        sendMessage = (Button)findViewById(R.id.sendMessage);

        coach_ID = getIntent().getIntExtra(KEY_COACH_ID,0);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(messageContent.getText().toString())){
                    AddPost send = new AddPost();
                    send.execute();

                }
                else{
                    Toast.makeText(AddPostActivity.this,getResources().getString(R.string.emptyMessageError),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private class AddPost extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Message,String> messDao = DaoManager.createDao(connectionSource, Message.class);
                TableUtils.createTableIfNotExists(connectionSource,Message.class);
                int rowNumb = (int)messDao.countOf();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Message mess = new Message(rowNumb,coach_ID,messageContent.getText().toString(),currentDate,null);
                messDao.create(mess);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddPostActivity.this,getResources().getString(R.string.post_added),Toast.LENGTH_LONG).show();
                        messageContent.setText("");
                    }
                });
            }catch(SQLException e ){

            }
            return null;
        }
    }
}
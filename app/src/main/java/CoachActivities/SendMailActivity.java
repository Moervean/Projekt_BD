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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Data.ConnectionClass;
import Model.Message;

public class SendMailActivity extends AppCompatActivity {

    private static final String KEY_USER_ID = "User_ID";
    private static final String KEY_USER_MAIL = "User_Mail";
    private static final String KEY_COACH_ID = "Coach_ID";
    private int user_ID;
    private int coach_ID;
    private EditText messageContent;
    private Button sendMessage;
    private String userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        user_ID = getIntent().getIntExtra(KEY_USER_ID,0);
        coach_ID = getIntent().getIntExtra(KEY_COACH_ID,0);
        messageContent = (EditText )findViewById(R.id.messageContent);
        sendMessage = (Button)findViewById(R.id.sendMessage);
        userMail = getIntent().getStringExtra(KEY_USER_MAIL);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(messageContent.getText().toString())){
                    SendMessage send = new SendMessage();
                    send.execute();

                }
                else{
                    Toast.makeText(SendMailActivity.this,getResources().getString(R.string.emptyMessageError),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class SendMessage extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Message,String> messDao = DaoManager.createDao(connectionSource, Message.class);
                TableUtils.createTableIfNotExists(connectionSource,Message.class);
                int rowNumb = (int)messDao.countOf();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Message mess = new Message(rowNumb,coach_ID,messageContent.getText().toString(),currentDate,userMail);
                messDao.create(mess);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SendMailActivity.this,getResources().getString(R.string.mailSent),Toast.LENGTH_LONG).show();
                        messageContent.setText("");
                    }
                });
            }catch(SQLException e ){

            }
            return null;
        }
    }
}
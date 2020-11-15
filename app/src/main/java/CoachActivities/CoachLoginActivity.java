package CoachActivities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.Coach;

public class CoachLoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private static final String KEY_COACH_ID = "coach_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailLog);
        password = (EditText)findViewById(R.id.passLog);
        login = (Button)findViewById(R.id.loginLog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(email.getText().toString())
                        && !TextUtils.isEmpty(password.getText().toString()))
                {
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();
                    Check check = new Check(CoachLoginActivity.this,mail,pass);
                    check.execute();


                }
                else{
                    Toast.makeText(CoachLoginActivity.this,getResources().getString(R.string.blankError),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private class Check extends AsyncTask<Void,Void,Void> {
        private Context ctx;
        private String mail,pass;

        public Check(Context ctx, String mail, String pass) {
            this.ctx = ctx;
            this.mail = mail;
            this.pass = pass;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Coach,String> loginDao = DaoManager.createDao(connectionSource,Coach.class);
                TableUtils.createTableIfNotExists(connectionSource,Coach.class);
                QueryBuilder<Coach,String> builder = loginDao.queryBuilder();
                builder.where().eq("mail",mail).and().eq("pass",pass);
                List<Coach> coach = loginDao.query(builder.prepare());
                if(coach.size() > 0){
                    Intent intent = new Intent(CoachLoginActivity.this, CoachMainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(KEY_COACH_ID,coach.get(0).getID_coach());
                    finish();
                    startActivity(intent);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx,getResources().getString(R.string.noUser),Toast.LENGTH_SHORT).show();
                        }
                    });
                        }


            }catch(SQLException e){

            }



            return null;
        }
    }
}

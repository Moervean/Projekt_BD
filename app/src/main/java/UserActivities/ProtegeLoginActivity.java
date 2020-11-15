package UserActivities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import Data.ConnectionClass;
import Model.User;

public class ProtegeLoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private static final String KEY_ID = "ID";
    private static final String KEY_MAIL = "MAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailLog);
        password = (EditText) findViewById(R.id.passLog);
        login = (Button) findViewById(R.id.loginLog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString())
                        && !TextUtils.isEmpty(password.getText().toString())) {
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();

                    Load check = new Load(ProtegeLoginActivity.this,mail,pass);
                    check.execute();

//
                } else {
                    Toast.makeText(ProtegeLoginActivity.this, getResources().getString(R.string.blankError), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public class Load extends AsyncTask<String, String, String> {
        Context ctx;
        String mail,pass;

        public Load(Context ctx, String mail, String pass) {
            this.ctx = ctx;
            this.mail = mail;
            this.pass = pass;
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
//
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<User, String> loginDao = DaoManager.createDao(connectionSource, User.class);
                TableUtils.createTableIfNotExists(connectionSource,User.class);
                QueryBuilder<User, String> builder = loginDao.queryBuilder();
                builder.where().eq("mail", mail).and().eq("password",pass);
                List<User> user = loginDao.query(builder.prepare());
                if (user.size() > 0 ) {
                    Intent intent = new Intent(ProtegeLoginActivity.this, ProtegeMainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(KEY_ID,user.get(0).getID());
                    intent.putExtra(KEY_MAIL,user.get(0).getMail());
                    finish();

                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx,getResources().getString(R.string.noUser),Toast.LENGTH_SHORT).show();
                        }
                    });
                }


//                Statement stmt = con.createStatement();
//                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

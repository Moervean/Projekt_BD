package UserActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import Data.ConnectionClass;
import Model.User;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.connButton);
        connectionClass = new ConnectionClass();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save dosave = new Save();
                dosave.execute("");
            }
        });
    }
    public class Save extends AsyncTask<String,String,String> {
        String z="basic";
        @Override
        protected String doInBackground(String... strings) {
            try{
//                Connection con = connectionClass.CONN();
//                if(con==null){
//                    z="No interne connection";
//                    return "No internet connection";
//                }
//                String query = "INSERT INTO `users`(`ID`, `name`, `nickname`) VALUES (5,'x','x')";
                ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:mysql://192.168.0.7:3306/dietary","vox","789");
                TableUtils.createTable(connectionSource,User.class);
                User us = new User(1,"haslo","Albert","Bielak","Albecrik_Bielaczek_69@wp.pl","123456789","all",1);
                Dao<User,String> userDao = DaoManager.createDao(connectionSource,User.class);
                userDao.create(us);




//                Statement stmt = con.createStatement();
//                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(),z,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }
}
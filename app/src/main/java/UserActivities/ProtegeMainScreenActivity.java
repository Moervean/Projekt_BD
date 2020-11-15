package UserActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.projekt_bd.R;

import CoachActivities.AddMealActivity;

public class ProtegeMainScreenActivity extends AppCompatActivity {
    private Button coaches;
    private Button diet;
    private Button workout;
    private static final String KEY_ID = "ID";
    private static final String KEY_MAIL = "MAIL";
    private int user_ID;
    private String user_mail;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.protegeMainMessages:
                Intent intent = new Intent(ProtegeMainScreenActivity.this,CheckMessagesActivity.class);
                intent.putExtra(KEY_MAIL,user_mail);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.protege_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protege_main_screen);

        user_ID = getIntent().getIntExtra(KEY_ID,0);
        user_mail = getIntent().getStringExtra(KEY_MAIL);

        coaches = (Button ) findViewById(R.id.coachesProtegesMain);
        diet = (Button)findViewById(R.id.dietButtonProteges);
        workout = (Button)findViewById(R.id.exerciseButtonProteges);

        coaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProtegeMainScreenActivity.this, CoachesListActivity.class);
                intent.putExtra(KEY_ID,user_ID);
                startActivity(intent);
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProtegeMainScreenActivity.this, ShowDietActivity.class);
                intent.putExtra(KEY_ID,user_ID);
                startActivity(intent);
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProtegeMainScreenActivity.this, UserWorkoutsListActivity.class);
                intent.putExtra(KEY_ID,user_ID);
                startActivity(intent);
            }
        });

    }
}
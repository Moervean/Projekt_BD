package CoachActivities;

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

public class CoachMainScreenActivity extends AppCompatActivity {
    private Button proteges;
    private Button diet;
    private Button workout;
    private static final String KEY_COACH_ID = "coach_ID";
    private int value;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.coach_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.coach_profile:
                Intent intent = new Intent(CoachMainScreenActivity.this,CoachProfileActivity.class);
                intent.putExtra(KEY_COACH_ID,value);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main_screen);

        value = getIntent().getIntExtra(KEY_COACH_ID,0);

        proteges = (Button)findViewById(R.id.protegesButtonProfile);
        diet = (Button)findViewById(R.id.dietButtonProfile);
        workout = (Button)findViewById(R.id.exerciseButtonProfile);

        proteges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachMainScreenActivity.this,ProtegesListActivity.class);
                intent.putExtra(KEY_COACH_ID,value);
                startActivity(intent);

            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachMainScreenActivity.this,DietListActivity.class);
                intent.putExtra(KEY_COACH_ID,value);
                startActivity(intent);
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachMainScreenActivity.this,WorkoutsListActivity.class);
                intent.putExtra(KEY_COACH_ID,value);
                startActivity(intent);
            }
        });
    }
}
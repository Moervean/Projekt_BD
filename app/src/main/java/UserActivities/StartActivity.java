package UserActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projekt_bd.R;

import CoachActivities.CoachLoginActivity;

public class StartActivity extends AppCompatActivity {
    private Button coach;
    private Button protege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);

        coach = (Button)findViewById(R.id.coachStartButton);
        protege = (Button)findViewById(R.id.protegeStartButton);

        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, CoachLoginActivity.class));
            }
        });
        protege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,ProtegeLoginActivity.class));
            }
        });
    }
}
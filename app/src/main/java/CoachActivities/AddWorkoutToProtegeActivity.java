package CoachActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.ConnectionClass;
import Model.AddWorkoutToProtegeRecyclerViewAdapter;
import Model.DatePickerFragment;
import Model.EditWorkoutRecyclerViewAdapter;
import Model.Exercise;
import Model.Workout;
import Model.Workout_Exercise;
import Model.WorkoutsRecyclerViewAdapter;

public class AddWorkoutToProtegeActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private RecyclerView workoutsList;
    private RecyclerView.LayoutManager layoutManager;
    private int user_ID;
    private LoadWorkouts loadWorkouts;
    private static final String KEY_USER_ID = "User_ID";
    private String data = "12/14/21";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_to_protege);
        View v = findViewById(android.R.id.content).getRootView();
        layoutManager = new LinearLayoutManager(this);
        workoutsList = (RecyclerView)findViewById(R.id.exerciseList);
        datePicker = (DatePicker)findViewById(R.id.saveDatePicker);
        workoutsList.setHasFixedSize(true);
        workoutsList.setLayoutManager(layoutManager);
        user_ID = getIntent().getIntExtra(KEY_USER_ID,0);
        Date date = new Date(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        String data = dateFormat.format(date);
        loadWorkouts = new LoadWorkouts();


        loadWorkouts.execute();



    }

    private class LoadWorkouts extends AsyncTask<String,String,String> {

        private List<Workout> list;
        @Override
        protected String doInBackground(String... strings) {
            ConnectionSource connectionSource = new ConnectionClass().buildConnect();

            try {


                list = new ArrayList<>();
                Dao<Workout,String> workoutsDao = DaoManager.createDao(connectionSource,Workout.class);
                TableUtils.createTableIfNotExists(connectionSource,Workout.class);
                for(Workout w : workoutsDao){
                    list.add(w);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        workoutsList.setAdapter( new AddWorkoutToProtegeRecyclerViewAdapter(list,AddWorkoutToProtegeActivity.this,datePicker,user_ID,getApplicationContext()));
                    }
                });
                connectionSource.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
package UserActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.projekt_bd.R;
import com.google.android.material.tabs.TabLayout;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.ConnectionClass;
import Model.EditWorkoutRecyclerViewAdapter;
import Model.Exercise;
import Model.Timetable;
import Model.Workout;
import Model.Workout_Exercise;

public class UserWorkoutsListActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private RecyclerView exerciseList;
    private RecyclerView.LayoutManager layoutManager;
    private static final String KEY_ID = "ID";
    private int user_ID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_workouts_list);

        datePicker = (DatePicker) findViewById(R.id.loadDatePicker);
        exerciseList = (RecyclerView) findViewById(R.id.userExerciseList);
        layoutManager = new LinearLayoutManager(this);
        exerciseList.setLayoutManager(layoutManager);
        exerciseList.setHasFixedSize(true);
        user_ID = getIntent().getIntExtra(KEY_ID, 0);

        LoadExercises loadExercises = new LoadExercises();
        loadExercises.execute();
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                LoadExercises loadExercises = new LoadExercises();
                loadExercises.execute();
            }
        });


    }

    private class LoadExercises extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Date date = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                String data = dateFormat.format(date);

                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Timetable, String> timetableDao = DaoManager.createDao(connectionSource, Timetable.class);
                TableUtils.createTableIfNotExists(connectionSource, Timetable.class);

                QueryBuilder<Timetable, String> timetableBuilder = timetableDao.queryBuilder();
                timetableBuilder.where().eq("user_ID", user_ID)
                        .and().eq("date", data);
                List<Timetable> timetable = timetableDao.query(timetableBuilder.prepare());

                Dao<Workout_Exercise, String> WEDao = DaoManager.createDao(connectionSource, Workout_Exercise.class);
                TableUtils.createTableIfNotExists(connectionSource, Workout_Exercise.class);
                QueryBuilder<Workout_Exercise, String> exerciseBuilder = WEDao.queryBuilder();
                final List<Exercise> exercises = new ArrayList<>();
                if(timetable.size()>0){
                exerciseBuilder.where().eq("workout_ID", timetable.get(0).getWorkout_ID());
                List<Workout_Exercise> WEList = WEDao.query(exerciseBuilder.prepare());


                Dao<Exercise, String> exerciseDao = DaoManager.createDao(connectionSource, Exercise.class);
                QueryBuilder<Exercise, String> exeBuilder = exerciseDao.queryBuilder();
                for (Workout_Exercise we : WEList) {
                    exeBuilder.where().eq("ID_exercise", we.getExericse_ID());
                    List<Exercise> exe = exerciseDao.query(exeBuilder.prepare());
                    exercises.add(exe.get(0));
                    exe.clear();
                }}
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    exerciseList.setAdapter(new EditWorkoutRecyclerViewAdapter(exercises,getApplicationContext()));
                    }
                });


            } catch (SQLException e) {

            }
            return null;
        }
    }
}
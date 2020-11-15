package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.Workout;
import Model.WorkoutDialog;
import Model.WorkoutsRecyclerViewAdapter;

public class WorkoutsListActivity extends AppCompatActivity implements WorkoutDialog.WorkoutDialogListener {

    private RecyclerView workoutslist;
    private FloatingActionButton addWorkoutButton;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_list);

        workoutslist = (RecyclerView)findViewById(R.id.workoutsRecyclerView);
        addWorkoutButton = (FloatingActionButton) findViewById(R.id.addWorkoutfloating);

        workoutslist.setHasFixedSize(true);
         layoutManager = new LinearLayoutManager(this);
         workoutslist.setLayoutManager(layoutManager);
        LoadWorkouts loadWorkouts = new LoadWorkouts();
        loadWorkouts.execute();
        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDialog workoutDialog = new WorkoutDialog();
                workoutDialog.show(getSupportFragmentManager(),"workout dialog");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadWorkouts loadWorkouts = new LoadWorkouts();
        loadWorkouts.execute();
    }

    @Override
    public void getTexts(String name, String category) {
        CreateWorkout createWorkout = new CreateWorkout(name,category);
        createWorkout.execute();

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
                        workoutslist.setAdapter( new WorkoutsRecyclerViewAdapter(list,WorkoutsListActivity.this));
                    }
                });
                connectionSource.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class CreateWorkout extends AsyncTask<Void,Void,Void> {
        private String name;
        private String category;
        private LoadWorkouts loadWorkouts = new LoadWorkouts();
        public CreateWorkout(String name, String category) {
            this.name = name;
            this.category = category;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Workout,String> workoutDao = DaoManager.createDao(connectionSource,Workout.class);
                TableUtils.createTableIfNotExists(connectionSource,Workout.class);
                int rowNumb = (int) workoutDao.countOf();

                Workout workout = new Workout(rowNumb,name,category);
                workoutDao.create(workout);
                connectionSource.close();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadWorkouts.execute();
                    }
                });

            }catch (Exception e){

            }
            return null;
        }
    }
}
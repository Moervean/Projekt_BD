package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.EditWorkoutRecyclerViewAdapter;
import Model.Exercise;
import Model.Workout_Exercise;

public class EditWorkoutActivity extends AppCompatActivity {

    private RecyclerView exerciseList;
    private List<Exercise> exercicses;
    private FloatingActionButton addExercise;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private int value;
    private static final String KEY_WORKOUT_ID = "workoutID";

    @Override
    protected void onResume() {
        super.onResume();
        LoadExercises loadExercises = new LoadExercises();
        loadExercises.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        exerciseList = (RecyclerView) findViewById(R.id.editWorkoutRV);
        addExercise = (FloatingActionButton) findViewById(R.id.editWorkoutFloating);
        exerciseList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        exerciseList.setLayoutManager(layoutManager);
        value = getIntent().getIntExtra(KEY_WORKOUT_ID,0);
        LoadExercises loadExercises = new LoadExercises();
        loadExercises.execute();
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(EditWorkoutActivity.this,AddExerciseToWorkoutActivity.class);
                intent.putExtra(KEY_WORKOUT_ID,value);
                startActivity(intent);
            }
        });



    }

    private class LoadExercises extends AsyncTask<Void,Void,Void> {
        private List<Exercise> exeList;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                exeList = new ArrayList<>();
                Dao<Workout_Exercise,String> exercisesDao = DaoManager.createDao(connectionSource,Workout_Exercise.class);
                TableUtils.createTableIfNotExists(connectionSource,Workout_Exercise.class);
                QueryBuilder<Workout_Exercise,String> builder = exercisesDao.queryBuilder();
                builder.where().eq("Workout_ID",value);

                List<Workout_Exercise> WElist =  exercisesDao.query(builder.prepare());
                Dao<Exercise,String> exerciseDao = DaoManager.createDao(connectionSource,Exercise.class);
                QueryBuilder<Exercise,String> builderED = exerciseDao.queryBuilder();

                for(Workout_Exercise we : WElist){
                    builderED.where().eq("ID_exercise",we.getExericse_ID());
                    List<Exercise>  exe =  exerciseDao.query(builderED.prepare());
                    exeList.add(exe.get(0));

                    exe.clear();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        exerciseList.setAdapter(new EditWorkoutRecyclerViewAdapter(exeList,EditWorkoutActivity.this));
                    }
                });


            }catch(SQLException e){

            }
            return null;
        }
    }
}
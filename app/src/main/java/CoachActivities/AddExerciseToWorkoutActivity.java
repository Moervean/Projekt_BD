package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.projekt_bd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.ConnectionClass;
import Model.AddExerciseToWorkoutRecyclerViewAdapter;
import Model.Exercise;
import Model.Workout;
import Model.Workout_Exercise;

public class AddExerciseToWorkoutActivity extends AppCompatActivity {

    private RecyclerView exercisesList;
    private int value;
    private static final String KEY_WORKOUT_ID = "workoutID";
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addExercise;
    private LoadExercises loadExercises;

    @Override
    protected void onResume() {
        super.onResume();
        loadExercises = new LoadExercises();
        loadExercises.execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exerciseto_workout);

        value = getIntent().getIntExtra(KEY_WORKOUT_ID,0);

        exercisesList = (RecyclerView) findViewById(R.id.addExerciseToWorkoutRV);
        addExercise =(FloatingActionButton)findViewById(R.id.addExerciseFloatingButton);
        exercisesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        exercisesList.setLayoutManager(layoutManager);

        loadExercises = new LoadExercises();
        loadExercises.execute();

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddExerciseToWorkoutActivity.this,AddExerciseActivity.class));
            }
        });

    }

    private class LoadExercises extends AsyncTask<Void,Void,Void> {
        private List<Exercise> list;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Exercise,String> exercisesDao = DaoManager.createDao(connectionSource,Exercise.class);
                TableUtils.createTableIfNotExists(connectionSource,Exercise.class);
                list = new ArrayList<>();
                for(Exercise w : exercisesDao){
                    list.add(w);
                }
                Dao<Workout_Exercise,String>weDao = DaoManager.createDao(connectionSource,Workout_Exercise.class);
                QueryBuilder<Workout_Exercise,String> builder = weDao.queryBuilder();
                builder.where().eq("Workout_ID",value);
                List<Workout_Exercise> WEList = weDao.query(builder.prepare());


                for(int i =0;i<list.size();i++){
                    for(int j = 0; j<WEList.size();j++){
                        if(list.get(i).getID_exercise() == WEList.get(j).getExericse_ID()){

                            list.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        exercisesList.setAdapter(new AddExerciseToWorkoutRecyclerViewAdapter(AddExerciseToWorkoutActivity.this, value, getIntent(),AddExerciseToWorkoutActivity.this, list));
                    }
                });
            }catch (SQLException e){

            }
            return null;
        }
    }
}
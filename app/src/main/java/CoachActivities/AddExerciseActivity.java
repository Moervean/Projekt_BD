package CoachActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import Data.ConnectionClass;
import Model.Exercise;
import Model.Meal;

public class AddExerciseActivity extends AppCompatActivity {
    private EditText exeDesc;
    private EditText muscle;
    private Spinner sets;
    private Spinner reps;
    private Button addExer;
    private Integer[] setsCount = {1,2,3,4,5};
    private Integer[] repsCount = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exeDesc = (EditText)findViewById(R.id.exerciseDescription);
        muscle = (EditText)findViewById(R.id.exerciseMuscle);
        sets = (Spinner)findViewById(R.id.setsSpinner);
        reps = (Spinner)findViewById(R.id.repsSpinner);
        addExer = (Button)findViewById(R.id.uploadExercise);

        ArrayAdapter<Integer> adapterSets = new ArrayAdapter<>(AddExerciseActivity.this,R.layout.spinner_layout,setsCount);
        ArrayAdapter<Integer> adapterReps = new ArrayAdapter<>(AddExerciseActivity.this,R.layout.spinner_layout,repsCount);

        sets.setAdapter(adapterSets);
        reps.setAdapter(adapterReps);

        addExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String desc = exeDesc.getText().toString();
                 String muscles = muscle.getText().toString();
                 int set = (int) sets.getSelectedItem();
                 int rep = (int) reps.getSelectedItem();

                 SaveExercise saveExercise = new SaveExercise(desc,muscles,set,rep);
                 saveExercise.execute();




            }
        });





    }

    private class SaveExercise extends AsyncTask<String,String,String> {
        String desc;
        String muscles;
        int set;
        int rep;

        public SaveExercise(String desc, String muscles, int set, int rep) {
            this.desc = desc;
            this.muscles = muscles;
            this.set = set;
            this.rep = rep;
        }

        @Override
        protected String doInBackground(String... strings) {
            if(!TextUtils.isEmpty(desc)){

                ConnectionSource connectionSource = null;
                try {

                    connectionSource = new ConnectionClass().buildConnect();
                    Dao<Exercise,String> exeDao = DaoManager.createDao(connectionSource,Exercise.class);
                    TableUtils.createTableIfNotExists(connectionSource,Exercise.class);
                    int numRows =  (int) exeDao.countOf();
                    Exercise exe = new Exercise(numRows,desc,muscles,set,rep);

                    exeDao.create(exe);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddExerciseActivity.this,getResources().getString(R.string.exerciseAdded),Toast.LENGTH_LONG).show();
                            exeDesc.setText("");
                            muscle.setText("");
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }




            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddExerciseActivity.this,getResources().getString(R.string.blankExeError),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return null;
        }
    }
}
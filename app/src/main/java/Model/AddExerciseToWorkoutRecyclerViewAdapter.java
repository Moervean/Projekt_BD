package Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import CoachActivities.AddExerciseToWorkoutActivity;
import CoachActivities.EditWorkoutActivity;
import Data.ConnectionClass;

public class AddExerciseToWorkoutRecyclerViewAdapter extends RecyclerView.Adapter<AddExerciseToWorkoutRecyclerViewAdapter.MyViewHolder> {
    private Context ctx;
    private int value;
    private static final String KEY_WORKOUT_ID = "workoutID";
    private Intent intent;
    private Activity activity;

    public AddExerciseToWorkoutRecyclerViewAdapter(Context ctx, int value, Intent intent,Activity activity, List<Exercise> exerciseList) {
        this.ctx = ctx;
        this.value = value;
        this.exerciseList = exerciseList;
        this.intent = intent;
        this.activity = activity;
    }

    public AddExerciseToWorkoutRecyclerViewAdapter(Context ctx, List<Exercise> exerciseList, int value) {
        this.ctx = ctx;
        this.exerciseList = exerciseList;
        this.value = value;
    }

    private List<Exercise>exerciseList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_exercise_row,parent,false);

        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Exercise exercise = exerciseList.get(position);

        holder.name.setText(exercise.getName());
        holder.muscle.setText(exercise.getMuscle());
        holder.reps.setText(String.valueOf( exercise.getReps()));
        holder.sets.setText(String.valueOf(exercise.getSets()));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExerToWorkout addexerToWorkout = new addExerToWorkout(exercise);
                addexerToWorkout.execute();

                activity.finish();
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView muscle;
        private TextView sets;
        private TextView reps;
        private Button add;

        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.addExerciseRowName);
            muscle = (TextView)itemView.findViewById(R.id.addExerciseRowMuscle);
            sets = (TextView)itemView.findViewById(R.id.addExerciseRowSets);
            reps = (TextView)itemView.findViewById(R.id.addExerciseRowReps);
            add = (Button)itemView.findViewById(R.id.addExerviseToWorkout);
        }
    }

    private class addExerToWorkout  extends AsyncTask<Void,Void,Void> {
        private Exercise exercise;

        public addExerToWorkout(Exercise exercise) {
            this.exercise = exercise;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ConnectionSource connectionSource = new ConnectionClass().buildConnect();
            try {
                Dao<Workout_Exercise,String> weDao = DaoManager.createDao(connectionSource,Workout_Exercise.class);
                Workout_Exercise we = new Workout_Exercise(value,exercise.getID_exercise());
                 TableUtils.createTableIfNotExists(connectionSource,Workout_Exercise.class);

                weDao.create(we);



                //TODO nie reestuj aktynowsci jeszcze raz wczytać liste

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

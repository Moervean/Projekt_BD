package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import java.util.List;

import CoachActivities.EditWorkoutActivity;

public class EditWorkoutRecyclerViewAdapter extends RecyclerView.Adapter<EditWorkoutRecyclerViewAdapter.MyViewHolder> {
    private List<Exercise> exerciseList;
    private Context ctx;

    public EditWorkoutRecyclerViewAdapter(List<Exercise> exerciseList, Context ctx) {
        this.exerciseList = exerciseList;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_row,parent,false);
        
        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        holder.name.setText(exercise.getName());
        holder.muscle.setText(exercise.getMuscle());
        holder.reps.setText(String.valueOf( exercise.getReps()));
        holder.sets.setText(String.valueOf(exercise.getSets()));

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


        public MyViewHolder(View view, Context ctx) {
            super(view);

            name = (TextView)view.findViewById(R.id.exerciseRowName);
            muscle = (TextView)view.findViewById(R.id.exerciseRowMuscle);
            sets = (TextView)view.findViewById(R.id.exerciseRowSets);
            reps = (TextView)view.findViewById(R.id.exerciseRowReps);

        }
    }

}

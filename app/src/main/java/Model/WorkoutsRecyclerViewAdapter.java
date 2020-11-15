package Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import org.w3c.dom.Text;

import java.util.List;

import CoachActivities.EditWorkoutActivity;


public class WorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.MyViewHolder> {

    private List<Workout> list;
    private Context context;
    private static final String KEY_WORKOUT_ID = "workoutID";

    public WorkoutsRecyclerViewAdapter(List<Workout> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_row, parent, false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsRecyclerViewAdapter.MyViewHolder holder, int position) {
        final Workout workout = list.get(position);
        holder.workoutCategory.setText(workout.getCategory());
        holder.workoutName.setText(workout.getName());
        holder.editWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditWorkoutActivity.class);
                intent.putExtra(KEY_WORKOUT_ID,workout.getID_workout());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView workoutName;
        private TextView workoutCategory;
        private Button editWorkout;

        public MyViewHolder(@NonNull View view, Context ctx) {
            super(view);

            //context = ctx;
            workoutName = (TextView) view.findViewById(R.id.workoutName);
            workoutCategory = (TextView) view.findViewById(R.id.workoutCategory);
            editWorkout = (Button) view.findViewById(R.id.editWorkout);
        }
    }
}

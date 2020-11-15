package Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import java.util.List;

public class CoachesListRecyclerViewAdapter extends RecyclerView.Adapter<CoachesListRecyclerViewAdapter.MyViewHolder> {
    private List<Coach> coaches;

    public CoachesListRecyclerViewAdapter(List<Coach> coaches) {
        this.coaches = coaches;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coach c = coaches.get(position);

        holder.name.setText(c.getName());
        holder.name.setText(c.getSecondName());
    }

    @Override
    public int getItemCount() {
        return coaches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView secondName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.coachName);
            secondName = (TextView) itemView.findViewById(R.id.coachName);
        }
    }
}

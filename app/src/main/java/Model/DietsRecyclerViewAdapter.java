package Model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import CoachActivities.EditDietActivity;

public class DietsRecyclerViewAdapter extends RecyclerView.Adapter<DietsRecyclerViewAdapter.MyViewHolder> {
    private List<Diet> dietList;
    private Context ctx;
    private static final String KEY_DIET_ID = "Diet_ID";

    public DietsRecyclerViewAdapter(List<Diet> dietList, Context ctx) {
        this.dietList = dietList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_row,parent,false);
        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Diet diet = dietList.get(position);
        holder.dietName.setText(diet.getName());
        holder.dietCategory.setText(diet.getCategory());
        holder.editDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, EditDietActivity.class);
                intent.putExtra(KEY_DIET_ID,diet.getID_diet());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dietName;
        private TextView dietCategory;
        private Button editDiet;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            dietName = (TextView)itemView.findViewById(R.id.dietName);
            dietCategory = (TextView)itemView.findViewById(R.id.dietCategory);
            editDiet = (Button)itemView.findViewById(R.id.editDiet);
        }
    }
}

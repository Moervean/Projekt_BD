package UserActivitesModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import java.util.List;

import Model.AddMealToDIetRecyclerViewAdapter;
import Model.Meal;
import UserActivities.ShowDietActivity;

public class ShowDietRecyclerViewAdapter extends RecyclerView.Adapter<ShowDietRecyclerViewAdapter.MyViewHolder> {
    private Context ctx;
    private List<Meal>mealList;

    public ShowDietRecyclerViewAdapter(Context ctx, List<Meal> mealList) {
        this.ctx = ctx;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row,parent,false);

        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Meal meal = mealList.get(position);

        holder.mealName.setText(meal.getName());
        holder.mealCategory.setText(meal.getType());
        holder.mealKcal.setText(String.valueOf(meal.getKcal()));
        if(meal.isLactose_Allergy()){
            holder.lactoseAllergy.setBackgroundColor(ctx.getColor(R.color.colorDarkGreen));
        }else{
            holder.lactoseAllergy.setBackgroundColor(ctx.getColor(R.color.czerwien));
        }
        if(meal.isNuts_Allergy()){
            holder.nutsAlergy.setBackgroundColor(ctx.getColor(R.color.colorDarkGreen));
        }else{
            holder.nutsAlergy.setBackgroundColor(ctx.getColor(R.color.czerwien));
        }
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mealName;
        private TextView mealCategory;
        private TextView mealKcal;
        private TextView nutsAlergy;
        private TextView lactoseAllergy;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            mealName = (TextView)itemView.findViewById(R.id.mealName);
            mealCategory = (TextView)itemView.findViewById(R.id.mealCategory);
            mealKcal = (TextView) itemView.findViewById(R.id.mealKcal);
            nutsAlergy= (TextView)itemView.findViewById(R.id.nutsAllergyMR);
            lactoseAllergy = (TextView)itemView.findViewById(R.id.lactoseAllergyMR);
        }
    }
}

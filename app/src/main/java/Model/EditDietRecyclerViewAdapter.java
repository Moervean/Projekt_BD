package Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import org.w3c.dom.Text;

import java.util.List;

public class EditDietRecyclerViewAdapter extends RecyclerView.Adapter<EditDietRecyclerViewAdapter.MyViewHolder> {
    private List<Meal> mealList;
    private Context context;

    public EditDietRecyclerViewAdapter(List<Meal> mealList, Context context) {
        this.mealList = mealList;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDietRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row,parent,false);
        return new MyViewHolder(view,context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull EditDietRecyclerViewAdapter.MyViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        holder.mealName.setText(meal.getName());
        holder.mealCategory.setText(meal.getType());
        holder.mealKcal.setText(String.valueOf( meal.getKcal()));
        if(meal.isLactose_Allergy()){
            holder.lactoseAllergy.setBackgroundColor(R.color.colorDarkGreen);
        }else{
            holder.lactoseAllergy.setBackgroundColor(R.color.czerwien);
        }
        if(meal.isNuts_Allergy()){
            holder.nutsAlergy.setBackgroundColor(R.color.colorDarkGreen);
        }else{
            holder.nutsAlergy.setBackgroundColor(R.color.czerwien);
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

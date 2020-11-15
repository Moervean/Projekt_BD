package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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

import CoachActivities.EditDietActivity;
import CoachActivities.EditWorkoutActivity;
import Data.ConnectionClass;

public class AddMealToDIetRecyclerViewAdapter extends RecyclerView.Adapter<AddMealToDIetRecyclerViewAdapter.MyViewHolder>{
    private Context ctx;
    private int value;
    private List<Meal> mealList;

    public AddMealToDIetRecyclerViewAdapter(Context ctx, int value, List<Meal> mealList) {
        this.ctx = ctx;
        this.value = value;
        this.mealList = mealList;
    }

    private static final String KEY_DIET_ID = "Diet_ID";
    @NonNull
    @Override
    public AddMealToDIetRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_meal_row,parent,false);

        return new MyViewHolder(view,ctx);
    }


    @Override
    public void onBindViewHolder(@NonNull AddMealToDIetRecyclerViewAdapter.MyViewHolder holder, int position) {
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
        holder.addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMealToDiet addMealToDiet = new AddMealToDiet(meal);
                addMealToDiet.execute();
            }
        });

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
        private Button addMeal;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            mealName = (TextView)itemView.findViewById(R.id.mealNameAMR);
            mealCategory = (TextView)itemView.findViewById(R.id.mealCategoryAMR);
            mealKcal = (TextView) itemView.findViewById(R.id.mealKcalAMR);
            nutsAlergy= (TextView)itemView.findViewById(R.id.nutsAllergyAMR);
            lactoseAllergy = (TextView)itemView.findViewById(R.id.lactoseAllergyAMR);
            addMeal = (Button)itemView.findViewById(R.id.addMealAMR);
        }
    }

    private class AddMealToDiet extends AsyncTask<Void,Void,Void> {
        private Meal meal;

        public AddMealToDiet(Meal meal) {
            this.meal = meal;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Diet_Meal,String> DMdao = DaoManager.createDao(connectionSource,Diet_Meal.class);
                TableUtils.createTableIfNotExists(connectionSource,Diet_Meal.class);
                Diet_Meal dm = new Diet_Meal(value,meal.getID_meal());
                DMdao.create(dm);
                ((Activity) ctx).finish();
                ctx.startActivity(((Activity) ctx).getIntent());


                ((Activity) ctx).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,ctx.getResources().getString(R.string.mealAddedToDiet),Toast.LENGTH_SHORT).show();
                    }
                });
            }catch(SQLException e){

            }
            return null;
        }
    }
}

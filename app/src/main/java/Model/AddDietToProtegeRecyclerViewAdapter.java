package Model;

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
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import CoachActivities.DietListActivity;
import CoachActivities.EditDietActivity;
import Data.ConnectionClass;

public class AddDietToProtegeRecyclerViewAdapter extends RecyclerView.Adapter<AddDietToProtegeRecyclerViewAdapter.MyViewHolder> {
    private List<Diet> dietList;
    private Context ctx;
    private static final String KEY_DIET_ID = "Diet_ID";
    private int value;

    public AddDietToProtegeRecyclerViewAdapter(List<Diet> dietList, Context ctx,int value) {
        this.dietList = dietList;
        this.ctx = ctx;
        this.value = value;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_row,parent,false);
        return new MyViewHolder(view,ctx);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Diet diet = dietList.get(position);
        holder.dietName.setText(diet.getName());
        holder.dietCategory.setText(diet.getCategory());
        holder.editDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDietToProtege addDietToProtege = new AddDietToProtege(holder.dietName.getText().toString(),holder.dietCategory.getText().toString());
                addDietToProtege.execute();
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

    private class AddDietToProtege extends AsyncTask<Void,Void,Void> {
        private String name;
        private String category;

        public AddDietToProtege(String name, String category) {
            this.name = name;
            this.category = category;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                TableUtils.createTableIfNotExists(connectionSource,Diet.class);
                Dao<Diet,String> dietsDao = DaoManager.createDao(connectionSource,Diet.class);
                int rowNumb = (int) dietsDao.countOf();
                QueryBuilder<Diet,String> builder = dietsDao.queryBuilder();
                builder.where().eq("Protege_ID",value);
                List<Diet> userDiet = dietsDao.query(builder.prepare());
                if(userDiet.size()> 0){
                    UpdateBuilder<Diet,String> updateBuilder = dietsDao.updateBuilder();
                    updateBuilder.where().eq("Protege_ID",value);
                    updateBuilder.updateColumnValue("name",name);
                    updateBuilder.updateColumnValue("category",category);
                    updateBuilder.update();
                }else{
                    Diet diet = new Diet(rowNumb,name,category,value);
                    dietsDao.create(diet);
                }

                
                connectionSource.close();

            }catch (SQLException | IOException e){

            }
            return null;
        }
    }
}

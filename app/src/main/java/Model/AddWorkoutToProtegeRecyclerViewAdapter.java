package Model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import CoachActivities.EditWorkoutActivity;
import Data.ConnectionClass;


public class AddWorkoutToProtegeRecyclerViewAdapter extends RecyclerView.Adapter<AddWorkoutToProtegeRecyclerViewAdapter.MyViewHolder> {

    private List<Workout> list;
    private Context context;
    private DatePicker datePicker;
    private int user_ID;
    private String date;
    private Context ctx;

    public AddWorkoutToProtegeRecyclerViewAdapter(List<Workout> list, Context context, DatePicker date, int user_ID,Context ctx) {
        this.list = list;
        this.context = context;
        this.datePicker = date;
        this.user_ID = user_ID;
        this.ctx = ctx;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_row, parent, false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Workout workout = list.get(position);
        holder.workoutCategory.setText(workout.getCategory());
        holder.workoutName.setText(workout.getName());
        holder.editWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWorkout addWorkout = new AddWorkout(workout.getID_workout());
                addWorkout.execute();
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

    private class AddWorkout extends AsyncTask<Void,Void,Void> {
        private int workoutID;

        public AddWorkout(int workoutID) {
            this.workoutID = workoutID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Date date = new Date(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ctx);
                String data = dateFormat.format(date);
                ConnectionSource connectionSource = new ConnectionClass().buildConnect();
                Dao<Timetable,String> timetables = DaoManager.createDao(connectionSource,Timetable.class);
                TableUtils.createTableIfNotExists(connectionSource,Timetable.class);
                QueryBuilder<Timetable,String> builder = timetables.queryBuilder();
                builder.where().eq("user_ID",user_ID)
                        .and().eq("date",data);
                List<Timetable> timetableList = timetables.query(builder.prepare());
                if(timetableList.size()>0){
                    UpdateBuilder<Timetable,String> updateBuilder = timetables.updateBuilder();
                    updateBuilder.where().eq("user_ID",user_ID)
                            .and().eq("date",data);
                    updateBuilder.updateColumnValue("workout_ID",workoutID);
                    updateBuilder.update();
                }else{
                    Timetable timetable = new Timetable(data,user_ID,workoutID);
                    timetables.create(timetable);
                }

                connectionSource.close();
            }catch (SQLException | IOException e){

            }
            return null;
        }
    }
}

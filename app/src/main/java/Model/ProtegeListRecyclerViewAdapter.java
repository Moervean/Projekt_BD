package Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_bd.R;

import org.w3c.dom.Text;

import java.nio.file.LinkPermission;
import java.util.List;

import CoachActivities.AddDietToProtegeActivity;
import CoachActivities.AddWorkoutToProtegeActivity;
import CoachActivities.SendMailActivity;

public class ProtegeListRecyclerViewAdapter extends RecyclerView.Adapter<ProtegeListRecyclerViewAdapter.MyViewHolder> {
    private List<User>userList;
    private Context ctx;
    private static final String KEY_USER_ID = "User_ID";
    private static final String KEY_COACH_ID = "Coach_ID";
    private static final String KEY_USER_MAIL = "User_Mail";
    private int value;

    public ProtegeListRecyclerViewAdapter(List<User> userList, Context ctx,int value) {
        this.userList = userList;
        this.ctx = ctx;
        this.value = value;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protege_row,parent,false);
        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final User user = userList.get(position);


        holder.protegeName.setText(user.getName());
        holder.protegeSecondName.setText(user.getSecondName());
        holder.protegePhoneNumber.setText(user.getPhoneNumber());
        holder.protegeEmail.setText(user.getMail());
        holder.protegeDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, AddDietToProtegeActivity.class);
                intent.putExtra(KEY_USER_ID,user.getID());
                ctx.startActivity(intent);
            }
        });
        holder.protegeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, AddWorkoutToProtegeActivity.class);
                intent.putExtra(KEY_USER_ID,user.getID());
                ctx.startActivity(intent);
            }
        });
        holder.emailToProtege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, SendMailActivity.class);
                intent.putExtra(KEY_USER_ID,user.getID());
                intent.putExtra(KEY_USER_MAIL,user.getMail());
                intent.putExtra(KEY_COACH_ID,value);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView protegeName;
        private TextView protegeSecondName;
        private TextView protegeEmail;
        private TextView protegePhoneNumber;
        private Button emailToProtege;
        private Button protegeDiet;
        private Button protegeWorkout;
        public MyViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            protegeName = (TextView)itemView.findViewById(R.id.protegeName);
            protegeSecondName = (TextView)itemView.findViewById(R.id.protegeSecondName);
            protegeEmail = (TextView)itemView.findViewById(R.id.protegeEmail);
            protegePhoneNumber = (TextView) itemView.findViewById(R.id.protegePhoneNumber);
            emailToProtege = (Button) itemView.findViewById(R.id.emailToProtege);
            protegeDiet = (Button)itemView.findViewById(R.id.protegeDiet);
            protegeWorkout = (Button)itemView.findViewById(R.id.protegeWorkout);
        }
    }
}

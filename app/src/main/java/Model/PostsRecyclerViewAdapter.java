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

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.MyViewHolder> {
    private List<Message> messages;

    public PostsRecyclerViewAdapter(List<Message> messages) {
        this.messages = messages;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message post = messages.get(position);

        holder.post_content.setText(post.getContent());
        holder.post_content.setText(post.getDate());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView post_content;
        private TextView post_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            post_content = (TextView)itemView.findViewById(R.id.post_content);
            post_date = (TextView)itemView.findViewById(R.id.post_date);
        }
    }
}

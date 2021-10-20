package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    Context context;
    List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerItems = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new UserViewHolder(recyclerItems);

//        View layoutView = LayoutInflater
//                .from(parent.getContext()).inflate(R.layout.product_card,
//                        parent, false);
//        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") int position) {

       // holder.userEmail.setText(users.get(position).getEmail());
        //holder.userName.setText(users.get(position).getUserName());

        if (users != null && position < users.size()) {
            User model = users.get(position);
            holder.userEmail.setText(model.getEmail());
            holder.userName.setText(String.valueOf(model.getUserName()));
            //int i = (int) (new Date().getTime()/1000);
            //String url = model.getImage()+"?data="+i;
            String url = "http://10.0.2.2:5000/" + model.getImage();
            Glide.with(context)
                    .load(url)
                    //.circleCrop()
                    .apply(new RequestOptions().override(300, 300))
                    .into(holder.userImage);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static final class UserViewHolder extends RecyclerView.ViewHolder{
        private View view;
        TextView userEmail;
        TextView userName;
        ImageView userImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;

            userEmail = itemView.findViewById(R.id.userEmail);
            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImg);
        }
        public View getView() {
            return view;
        }
    }

}

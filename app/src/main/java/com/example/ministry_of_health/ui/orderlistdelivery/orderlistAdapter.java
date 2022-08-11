package com.example.ministry_of_health.ui.orderlistdelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.Users;

import java.util.List;

public class orderlistAdapter extends RecyclerView.Adapter<orderlistAdapter.orderlistViewHolder> {
    private Context context;
    private List<Users> usersList;

    public orderlistAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList=usersList;
    }

    @NonNull
    @Override
    public orderlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.useritem,parent,false);
        return new orderlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderlistViewHolder holder, int position) {
        Users users = usersList.get(position);
        holder.setValues(users);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    class orderlistViewHolder extends RecyclerView.ViewHolder
    {
        private Users users;
        TextView textViewname,textViewid,textViewbirthday,textViewgender;
        Button buttonupdate;
        ImageView image1,image2,image3,image4;

        public orderlistViewHolder(View itemView) {
            super(itemView);
            textViewname=itemView.findViewById(R.id.textViewname);
            textViewid=itemView.findViewById(R.id.textViewid);
            textViewbirthday=itemView.findViewById(R.id.textViewbirthday);
            textViewgender=itemView.findViewById(R.id.textViewgender);
            buttonupdate=itemView.findViewById(R.id.ButtonUpdate);
            image1=itemView.findViewById(R.id.imageView37);
            image2=itemView.findViewById(R.id.imageView38);
            image3=itemView.findViewById(R.id.imageView39);
            image4=itemView.findViewById(R.id.imageView40);
            buttonupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Users users = usersList.get(pos);
                    idinfo.setIduser(users.getId_number());
                    idinfo.setUsername(users.getName());
                    Navigation.findNavController(view).navigate(R.id.orderlistdelivery2);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });

        }

        public void setValues(Users users)
        {
            image1.setImageResource(R.drawable.profile);
            image2.setImageResource(R.drawable.idcard);
            image3.setImageResource(R.drawable.telephone);
            image4.setImageResource(R.drawable.maleandfemale);
            buttonupdate.setText("Show");
            textViewname.setText(users.getName());
            textViewid.setText(users.getId_number());
            textViewgender.setText(users.getGender());
            textViewbirthday.setText(users.getPhone());
        }
    }
}

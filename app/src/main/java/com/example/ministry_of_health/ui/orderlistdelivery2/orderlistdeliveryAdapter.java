package com.example.ministry_of_health.ui.orderlistdelivery2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;

import java.util.ArrayList;
import java.util.List;

public class orderlistdeliveryAdapter extends RecyclerView.Adapter<orderlistdeliveryAdapter.orderlistdelivery2ViewHolder> {
    private Context context;
    private List<Medicine> medicineList;

    public orderlistdeliveryAdapter(Context context, List<Medicine>medicineList){
        this.context=context;
        this.medicineList=medicineList;
    }
    @NonNull
    @Override
    public orderlistdelivery2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.userlist,parent,false);
        return new orderlistdelivery2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderlistdelivery2ViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.setValues(medicine);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }


    class orderlistdelivery2ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView medicineImageView;
        private TextView nameTextView;

        public orderlistdelivery2ViewHolder(View itemView) {
            super(itemView);
            medicineImageView = itemView.findViewById(R.id.imageView35);
            nameTextView = itemView.findViewById(R.id.textView17);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });
        }

        public void setValues(Medicine medicine)
        {
            Glide.with(context).load(medicine.getImg()).into(medicineImageView);
            nameTextView.setText(medicine.getNamemedicine());
        }
    }
}

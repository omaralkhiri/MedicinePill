package com.example.ministry_of_health.ui.medicine;

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

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.medicineViewHolder> {
    private Context context;
    private List<Medicine>medicineList;
    public MedicineAdapter(Context context,List<Medicine>medicineList){
        this.context=context;
        this.medicineList=medicineList;
    }
    @NonNull
    @Override
    public medicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.medicine_item,parent,false);
        return new medicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.setValues(medicine);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    class medicineViewHolder extends RecyclerView.ViewHolder
    {
        //private Medicine medicine;

        private ImageView medicineImageView;
        private TextView nameTextView;
        private TextView takeTextView;
        private TextView quentityTextView;


        public medicineViewHolder(View itemView) {
            super(itemView);
            medicineImageView = itemView.findViewById(R.id.imgmedicine);
            nameTextView = itemView.findViewById(R.id.txt_name);
            takeTextView = itemView.findViewById(R.id.txt_take);
            quentityTextView = itemView.findViewById(R.id.txt_quentity);

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
         takeTextView.setText(medicine.getTakemedicine());
         String quentity1=medicine.getQuentitymedicine()+"";
         quentityTextView.setText(quentity1);
        }
    }
}

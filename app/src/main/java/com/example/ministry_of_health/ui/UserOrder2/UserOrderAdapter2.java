package com.example.ministry_of_health.ui.UserOrder2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrderAdapter2 extends RecyclerView.Adapter<UserOrderAdapter2.medicineViewHolder2> {
    private Context context;
    private List<Medicine> medicineList;
    private RequestQueue queue;

    public UserOrderAdapter2(Context context,List<Medicine>medicineList){
        this.context=context;
        this.medicineList=medicineList;
        queue= Volley.newRequestQueue(context);
    }
    @NonNull
    @Override
    public medicineViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.medicne_item_addordelete,parent,false);
        return new medicineViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicineViewHolder2 holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.setValues(medicine);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    class medicineViewHolder2 extends RecyclerView.ViewHolder
    {
        private ImageView medicineImageView,removemedicine;
        private TextView nameTextView;
        private String URL="http://"+idinfo.getIpaddress()+"/health_app/deleteitem_fromtable_orderandmedicineuser.php";


        public medicineViewHolder2(View itemView) {
            super(itemView);
            medicineImageView = itemView.findViewById(R.id.imageitem);
            removemedicine=itemView.findViewById(R.id.removeitem);
            nameTextView = itemView.findViewById(R.id.nameitem);

            removemedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Medicine medicine=medicineList.get(pos);
                    idinfo.setMedicineid(medicine.getMedicineid());
                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true")){
                                Toast.makeText(context.getApplicationContext(), "Successful Delette", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("id", idinfo.getIduser());
                            params.put("medicineid",+idinfo.getMedicineid()+"");
                            return params;
                        }
                    };
                    queue.add(request);
                }
            });

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
            removemedicine.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
            nameTextView.setText(medicine.getNamemedicine());

        }
    }
}


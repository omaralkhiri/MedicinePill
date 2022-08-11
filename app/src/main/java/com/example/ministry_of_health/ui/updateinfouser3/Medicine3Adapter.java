package com.example.ministry_of_health.ui.updateinfouser3;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Medicine3Adapter extends RecyclerView.Adapter<Medicine3Adapter.medicineViewHolder> {
    private Context context;
    private List<Medicine> medicineList;
    private RequestQueue queue;
    public Medicine3Adapter(Context context,List<Medicine>medicineList){
        this.context=context;
        this.medicineList=medicineList;
        queue= Volley.newRequestQueue(context);
    }
    @NonNull
    @Override
    public medicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.medicne_item_addordelete,parent,false);
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
    public void filterList(ArrayList<Medicine> filteredlist) {
        medicineList=filteredlist;
        notifyDataSetChanged();
    }

    class medicineViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView medicineImageView,addmedicine;
        private TextView nameTextView;
        private String URL="http://"+idinfo.getIpaddress()+"/health_app/add_medicine_to_user.php";

        public medicineViewHolder(View itemView) {
            super(itemView);
            medicineImageView = itemView.findViewById(R.id.imageitem);
            addmedicine=itemView.findViewById(R.id.removeitem);
            nameTextView = itemView.findViewById(R.id.nameitem);
            addmedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Medicine medicine = medicineList.get(pos);
                    int medicineid=medicine.getMedicineid();
                    String namemedicine=medicine.getNamemedicine();
                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("true"))
                                Toast.makeText(context.getApplicationContext(), "Successful Add", Toast.LENGTH_SHORT).show();
                            else if (response.trim().equals("false"))
                                Toast.makeText(context.getApplicationContext(), "This medicine has been added before", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>params=new HashMap<>();
                            params.put("userid", idinfo.getIduser());
                            params.put("medicineid",medicineid+"");
                            params.put("namemedicine",namemedicine);
                            params.put("branch_id",idinfo.getBranch_id());
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
            addmedicine.setImageResource(R.drawable.ic_baseline_add_circle_24);
            Glide.with(context).load(medicine.getImg()).into(medicineImageView);
            nameTextView.setText(medicine.getNamemedicine());
        }
    }
}

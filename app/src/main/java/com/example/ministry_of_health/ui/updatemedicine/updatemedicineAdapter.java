package com.example.ministry_of_health.ui.updatemedicine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class updatemedicineAdapter extends RecyclerView.Adapter<updatemedicineAdapter.updatemedicineViewHolder> {
    private Context context;
    private List<Medicine> medicineList;
    private RequestQueue queue;

    public updatemedicineAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
        queue= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public updatemedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.upadate_medicine_item,parent,false);
        return new updatemedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull updatemedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.setValues(medicine );
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public void filterList(ArrayList<Medicine> filteredlist) {
        medicineList=filteredlist;
        notifyDataSetChanged();
    }

    class updatemedicineViewHolder extends RecyclerView.ViewHolder
    {
       // private Medicine medicine ;
       private ImageView imgmedicine;
       private EditText namemedicine,takemedicine,quentity1,quentity2;
       private Button Remove,update;
       private String URL1="http://"+ idinfo.getIpaddress()+"/health_app/delete_medicine.php";
       private String URL2="http://"+idinfo.getIpaddress()+"/health_app/update_medicine.php";
       private String name_medicine,take_medicine,quentity_medicine1,quentity_medicine2;


        public updatemedicineViewHolder(View itemView) {
            super(itemView);
          namemedicine=itemView.findViewById(R.id.editname);
          takemedicine=itemView.findViewById(R.id.edittakemedicine);
          quentity1=itemView.findViewById(R.id.editquentity);
          quentity2=itemView.findViewById(R.id.editquentityinstore);
          imgmedicine=itemView.findViewById(R.id.imagemedicine);
          Remove=itemView.findViewById(R.id.button3);
          update=itemView.findViewById(R.id.button2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });
            Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name_medicine=namemedicine.getText().toString();

                            StringRequest request=new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.trim().equals("true")){
                                        Toast.makeText(context.getApplicationContext(), "delete successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context.getApplicationContext(),"Please Don't Delete Name of Medicine to Remove Medicine",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map <String, String> params=new HashMap<>();
                                    params.put("name",name_medicine);
                                    params.put("branch_id",idinfo.getBranch_id());
                                    return params;
                                }
                            };
                            queue.add(request);
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name_medicine=namemedicine.getText().toString();
                    take_medicine=takemedicine.getText().toString();
                    quentity_medicine1=quentity1.getText().toString();
                    quentity_medicine2=quentity2.getText().toString();
                StringRequest request=new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       if (response.trim().equals("true")){
                           Toast.makeText(context.getApplicationContext(),"succesful Update",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(context.getApplicationContext(), "Please fill in all the Information!!", Toast.LENGTH_SHORT).show();
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
                        params.put("branch_id",idinfo.getBranch_id());
                        params.put("name",name_medicine);
                        params.put("take",take_medicine);
                        params.put("quentity1",quentity_medicine1);
                        params.put("quentity2",quentity_medicine2);
                        return params;
                    }
                };
                queue.add(request);
                }
            });
        }

        public void setValues(Medicine medicine)
        {
           Glide.with(context).load(medicine.getImg()).into(imgmedicine);
            namemedicine.setText(medicine.getNamemedicine());
            takemedicine.setText(medicine.getTakemedicine());
            String quentitymedicine=medicine.getQuentitymedicine()+"";
            String quentityinstore=medicine.getQuentityinstore()+"";
            quentity1.setText(quentitymedicine);
            quentity2.setText(quentityinstore);

        }

    }
}
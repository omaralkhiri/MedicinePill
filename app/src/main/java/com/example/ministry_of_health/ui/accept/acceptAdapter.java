package com.example.ministry_of_health.ui.accept;

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
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.Users;
import com.example.ministry_of_health.idinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class acceptAdapter extends RecyclerView.Adapter<acceptAdapter.accept_rejectViewHolder>{
    private Context context;
    private List<Users> usersList;
    private RequestQueue queue;

    public acceptAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
        queue=Volley.newRequestQueue(context);
    }
    @NonNull
    @Override
    public accept_rejectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.accept_users, parent, false);
        return new accept_rejectViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull accept_rejectViewHolder holder, int position) {
        Users users = usersList.get(position);
        holder.setValues(users);
    }
    @Override
    public int getItemCount() {
        return usersList.size();
    }


    class accept_rejectViewHolder extends RecyclerView.ViewHolder
    {
       // private Users users;

        private TextView username,id_user,gender_user,birthday_user,phone_user,job_user,pass_user;
        private ImageView imgtrue,imgcancel,imgidcard;
        private String URL="http://"+ idinfo.getIpaddress()+"/health_app/accept_delete.php";
        private String URL1="http://"+idinfo.getIpaddress()+"/health_app/delete.php";
        private String name,id,birthday,gender,phone,pass,job;
        public accept_rejectViewHolder(View itemView) {
            super(itemView);
            imgidcard=itemView.findViewById(R.id.imageView9);
            username=itemView.findViewById(R.id.username);
            id_user=itemView.findViewById(R.id.id_user);
            gender_user=itemView.findViewById(R.id.gender_user);
            birthday_user=itemView.findViewById(R.id.birthday_user);
            phone_user=itemView.findViewById(R.id.phone_user);
            imgtrue=itemView.findViewById(R.id.imgtrue);
            imgcancel=itemView.findViewById(R.id.imgcancel);
            job_user=itemView.findViewById(R.id.job_user);
            pass_user=itemView.findViewById(R.id.pass_user);

            imgtrue.setOnClickListener(new View.OnClickListener() {
                @Override
             public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Users users = usersList.get(pos);
                    name=users.getName();
                    id=users.getId_number();
                    birthday=users.getBirthday();
                    gender=users.getGender();
                    phone=users.getPhone();
                    pass=users.getPassword();
                    job=users.getJob();

                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                         if (response.trim().equals("true")){
                             Toast.makeText(context.getApplicationContext(), "The data has been transferred successfully", Toast.LENGTH_SHORT).show();
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
                            params.put("fname",name);
                            params.put("idnum",id);
                            params.put("birth",birthday);
                            params.put("gender",gender);
                            params.put("phone",phone);
                            params.put("pass",pass);
                            params.put("job",job);
                            params.put("branch_id",idinfo.getBranch_id());
                            return params;
                        }
                    };
                    queue.add(request);
                }
             });
            imgcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Users users = usersList.get(pos);
                    id=users.getId_number();
                    StringRequest request=new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                         if (response.trim().equals("true"))
                         {
                             Toast.makeText(context.getApplicationContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                         }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params =new HashMap<>();
                            params.put("idnum",id);
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

        public void setValues(Users users)
        {
            Glide.with(context).load(users.getImgidcard()).into(imgidcard);
            imgcancel.setImageResource(R.drawable.ic_baseline_cancel_24);
         imgtrue.setImageResource(R.drawable.ic_baseline_check_circle_24);
         username.setText(users.getName());
         id_user.setText(users.getId_number());
         gender_user.setText(users.getGender());
         birthday_user.setText(users.getBirthday());
         phone_user.setText(users.getPhone());
         pass_user.setText(users.getPassword());
         job_user.setText(users.getJob());
        }
    }


}


package com.example.ministry_of_health.ui.RemoveAccount;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
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
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.Users;
import com.example.ministry_of_health.idinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.userViewHolder> {
    private Context context;
    private List<Users> UsersList;
    private RequestQueue queue;
    public AdapterUsers(Context context, List<Users> UserList) {
        this.context = context;
        this.UsersList = UserList;
        queue= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.useritem,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Users users = UsersList.get(position);

        holder.setValues(users);
    }

    @Override
    public int getItemCount() {
        return UsersList.size();
    }
    public void filterList(ArrayList<Users> filteredlist) {
        UsersList=filteredlist;
        notifyDataSetChanged();
    }
    class userViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nameuser;
        private TextView iduser;
        private TextView phoneuser;
        private TextView genderuser;
        private Button remove;
        ImageView image1,image2,image3,image4;
        public userViewHolder(View itemView) {
            super(itemView);
            nameuser = itemView.findViewById(R.id.textViewname);
            iduser= itemView.findViewById(R.id.textViewid);
            phoneuser = itemView.findViewById(R.id.textViewbirthday);
            genderuser = itemView.findViewById(R.id.textViewgender);
            image1=itemView.findViewById(R.id.imageView37);
            image2=itemView.findViewById(R.id.imageView38);
            image3=itemView.findViewById(R.id.imageView39);
            image4=itemView.findViewById(R.id.imageView40);
            remove=itemView.findViewById(R.id.ButtonUpdate);
            String Url="http://"+ idinfo.getIpaddress()+"/health_app/finish_user_account.php";
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Users users = UsersList.get(pos);
                    String id=users.getId_number();
                            StringRequest request=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params=new HashMap<>();
                                    params.put("id_number",id);
                                    return params;
                                }
                            };
                            queue.add(request);
                          Toast.makeText(itemView.getContext(), "Successful Remove", Toast.LENGTH_SHORT).show();
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
            remove.setText("Remove");
           nameuser.setText(users.getName());
           iduser.setText(users.getId_number());
           phoneuser.setText(users.getPhone());
           genderuser.setText(users.getGender());
            image1.setImageResource(R.drawable.profile);
            image2.setImageResource(R.drawable.idcard);
            image3.setImageResource(R.drawable.telephone);
            image4.setImageResource(R.drawable.maleandfemale);
        }
    }
}

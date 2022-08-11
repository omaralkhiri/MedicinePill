package com.example.ministry_of_health.ui.accept;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.Users;
import com.example.ministry_of_health.idinfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class accept_rejectFragment extends Fragment {
    private RecyclerView recyclerView;
    private acceptAdapter adapter;
    private List<Users> usersList;
    private RequestQueue queue;
    private String URL = "http://"+ idinfo.getIpaddress()+"/health_app/alluser.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_reject, container, false);

        recyclerView = view.findViewById(R.id.UsersRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        URL+="?branch="+idinfo.getBranch_id();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                builduserlist(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);


        return view;
    }

    public void builduserlist(JSONObject response) {
        try {
            usersList = new ArrayList<Users>();
            JSONArray allusers=response.getJSONArray("user");
            for (int i=0;i<allusers.length();i++){
                JSONObject oneuser=allusers.getJSONObject(i);
                String name=oneuser.getString("full_name");
                String ID=oneuser.getString("ID_number");
                String birthday=oneuser.getString("birthday");
                String gender=oneuser.getString("gender");
                String phone=oneuser.getString("phone");
                String pass=oneuser.getString("pass");
                String job=oneuser.getString("job");
                String img=idinfo.getPathimag()+oneuser.getString("idcard_image");
                Users users=new Users(name,ID,birthday,gender,phone,pass,job,img);
                usersList.add(users);
            }
            adapter = new acceptAdapter(getActivity().getApplicationContext(), usersList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        } catch (Exception x) {
         x.printStackTrace();
        }
    }
}
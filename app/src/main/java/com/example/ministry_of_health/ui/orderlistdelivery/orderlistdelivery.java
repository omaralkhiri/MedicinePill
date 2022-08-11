package com.example.ministry_of_health.ui.orderlistdelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.Users;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class orderlistdelivery extends Fragment {

    private RecyclerView recyclerView;
    private orderlistAdapter adapter;
    private List<Users> usersList;
    String URL="http://"+ idinfo.getIpaddress()+"/health_app/orderlistcompleted.php";
    private RequestQueue queue;

    public static orderlistdelivery newInstance() {
        return new orderlistdelivery();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.orderlistdelivery_fragment, container, false);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        recyclerView =view.findViewById(R.id.orderlistRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        URL+="?branch_id="+idinfo.getBranch_id();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                buildusersList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return view;
    }


    public void buildusersList(JSONObject response) {
        try {
            usersList = new ArrayList<Users>();
            JSONArray allusers=response.getJSONArray("user");
            for (int i=0;i<allusers.length();i++){
                JSONObject oneuser=allusers.getJSONObject(i);
                String name=oneuser.getString("full_name");
                String ID=oneuser.getString("ID_number");
                idinfo.setIduser(oneuser.getString("ID_number"));
                idinfo.setIduser(oneuser.getString("ID_number"));
                String birthday=oneuser.getString("phone");
                String gender=oneuser.getString("gender");
                idinfo.setLatitude(oneuser.getDouble("Latitude"));
                idinfo.setLongitude(oneuser.getDouble("Longitude"));
                Users users=new Users(name,ID,birthday,gender);
                usersList.add(users);
            }
            adapter = new orderlistAdapter(getActivity().getApplicationContext(), usersList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
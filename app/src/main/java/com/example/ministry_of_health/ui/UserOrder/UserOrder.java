package com.example.ministry_of_health.ui.UserOrder;

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

public class UserOrder extends Fragment {


    private RecyclerView recyclerView;
    private UserOrderAdapter adapter;
    private List<Users> usersList;
    String URL="http://"+idinfo.getIpaddress()+"/health_app/orderlistusers.php";
    private RequestQueue queue;

    public static UserOrder newInstance() {
        return new UserOrder();
    }

    @Override
    public void onStop() {
        super.onStop();
        queue.cancelAll("03");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.user_order_fragment, container, false);

        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        recyclerView =view.findViewById(R.id.usersListRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        URL+="?branch="+idinfo.getBranch_id();
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
        request.setTag("03");
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
                String birthday=oneuser.getString("phone");
                String gender=oneuser.getString("gender");
                Users users=new Users(name,ID,birthday,gender);
                usersList.add(users);
            }
            adapter = new UserOrderAdapter(getActivity().getApplicationContext(), usersList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
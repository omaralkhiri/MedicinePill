package com.example.ministry_of_health.ui.RemoveAccount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.Users;
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.ui.UserOrder.UserOrderAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RemoveAccount extends Fragment {
    private RecyclerView recyclerView;
    private AdapterUsers adapter;
    private List<Users> usersList;
    private RequestQueue queue;
    private String URL="http://"+idinfo.getIpaddress()+"/health_app/getalluser.php";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_remove_account, container, false);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        EditText search =view.findViewById(R.id.editTextTextsearch);
        swipeRefreshLayout=view.findViewById(R.id.frach);

        request();

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
       @Override
       public void onRefresh() {
        request();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
         }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }
    private void filter(String text){
        ArrayList<Users> filteredlist=new ArrayList<>();
        for (Users item:usersList){
            if (item.getId_number().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }

    public void buildusersList(JSONObject response) {
        try {
            usersList = new ArrayList<Users>();
            JSONArray allusers=response.getJSONArray("user");
            for (int i=0;i<allusers.length();i++){
                JSONObject oneuser=allusers.getJSONObject(i);
                String name=oneuser.getString("full_name");
                String ID=oneuser.getString("ID_number");
                String phone=oneuser.getString("phone");
                String gender=oneuser.getString("gender");
                Users users=new Users(name,ID,phone,gender);
                usersList.add(users);
            }
            adapter = new AdapterUsers(getActivity().getApplicationContext(), usersList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    private void request(){
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

    }
}
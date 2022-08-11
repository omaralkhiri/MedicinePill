package com.example.ministry_of_health.ui.updateinfouser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class updateuserinformation extends Fragment {


    private RecyclerView recyclerView;
    private UpdateuserAdapter adapter;
    private List<Users> usersList;
    private String URL="http://"+ idinfo.getIpaddress()+"/health_app/updatealluser_medicine.php";
    private RequestQueue queue;

    public static updateuserinformation newInstance() {
        return new updateuserinformation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.updateuserinformation_fragment, container, false);

        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        recyclerView =view.findViewById(R.id.User_RV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        EditText editTextsearach=view.findViewById(R.id.Searachuser);
        editTextsearach.addTextChangedListener(new TextWatcher() {
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
                String birthday=oneuser.getString("phone");
                String gender=oneuser.getString("gender");
                Users users=new Users(name,ID,birthday,gender);
                usersList.add(users);
            }
            adapter = new UpdateuserAdapter(getActivity().getApplicationContext(), usersList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    private void filter(String text){
        ArrayList<Users> filteredlist=new ArrayList<>();
        for (Users item:usersList){
            if (item.getId_number().contains(text)||item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }


}
package com.example.ministry_of_health.ui.UserOrder3;

import androidx.lifecycle.ViewModelProvider;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.ui.updateinfouser3.Medicine3Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrder3 extends Fragment {
    private RecyclerView recyclerView;
    private UserOrderAdapter3 adapter;
    private List<Medicine> medicineList;
    private EditText editTextsearch;
    RequestQueue queue;
    String URL="http://"+ idinfo.getIpaddress()+"/health_app/view_medicinelist.php";
    public static UserOrder3 newInstance() {
        return new UserOrder3();
    }

    @Override
    public void onStop() {
        super.onStop();
        queue.cancelAll("01");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.user_order3_fragment, container, false);
        editTextsearch=view.findViewById(R.id.editTextTextPersonName2);
        recyclerView = view.findViewById(R.id.medicineviewRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buildmedicinelist(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("BID", idinfo.getBranch_id());
                return params;
            }
        };
        request.setTag("01");
        queue.add(request);



        editTextsearch.addTextChangedListener(new TextWatcher() {
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
        ArrayList<Medicine> filteredlist=new ArrayList<>();
        for (Medicine item:medicineList){
            if (item.getNamemedicine().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }
    public void buildmedicinelist(String response){
        try {
            medicineList = new ArrayList<Medicine>();
            JSONObject object=new JSONObject(response);
            JSONArray allmedicine=object.getJSONArray("medicine");
            for (int i=0;i<allmedicine.length();i++){
                JSONObject onemedicine=allmedicine.getJSONObject(i);
                int medicineid=onemedicine.getInt("medicine_id");
                String image=idinfo.getPathImg()+onemedicine.getString("image_medicine");
                String imgmedicine=image;
                String namemedicine=onemedicine.getString("name_medicine");
                Medicine medicine=new Medicine(medicineid,imgmedicine,namemedicine);
                medicineList.add(medicine);
            }
            adapter = new UserOrderAdapter3(getActivity().getApplicationContext(),medicineList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
}
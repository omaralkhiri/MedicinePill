package com.example.ministry_of_health.ui.orderlistdelivery2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class orderlistdelivery2 extends Fragment {


    private RecyclerView recyclerView;
    private orderlistdeliveryAdapter adapter;
    private List<Medicine> medicineList;
    private RequestQueue queue;
    private String URL="http://"+ idinfo.getIpaddress()+"/health_app/medicinelistorder.php";
    public static orderlistdelivery2 newInstance() {
        return new orderlistdelivery2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.orderlistdelivery2_fragment, container, false);
        recyclerView = view.findViewById(R.id.ordermedicineRV);
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
                Map<String,String> params=new HashMap<>();
                params.put("id_num", idinfo.getIduser());
                return params;
            }};

        queue.add(request);
        Button startdelivery=view.findViewById(R.id.button5);
        startdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_orderlistdelivery2_to_mapsFragment);
            }
        });
        return view;
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
            adapter = new orderlistdeliveryAdapter(getActivity().getApplicationContext(),medicineList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
}
package com.example.ministry_of_health.ui.medicine;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.databinding.FragmentMedicineBinding;
import com.example.ministry_of_health.idinfo;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class medicineFragment extends Fragment {

    private FragmentMedicineBinding binding;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private String URL="http://"+idinfo.getIpaddress()+"/health_app/allmedicine.php";
    RequestQueue queue;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medicine,container,false);
         String id= idinfo.getId();

        recyclerView = root.findViewById(R.id.medicine_RV);
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_num",id);
                return params;
            }
        };

        queue.add(request);
        return root;
    }
    private void buildmedicinelist(String response){
        try {
            medicineList = new ArrayList<Medicine>();
            JSONObject object=new JSONObject(response);
            JSONArray allmedicine=object.getJSONArray("medicine");
            for (int i=0;i<allmedicine.length();i++){
                JSONObject onemedicine=allmedicine.getJSONObject(i);
                String image=idinfo.getPathImg()+onemedicine.getString("image_medicine");
                String imgmedicine=image;
                String namemedicine=onemedicine.getString("name_medicine");
                String takemedicine=onemedicine.getString("take_medicine");
                int quentity=onemedicine.getInt("quantity");
               Medicine medicine=new Medicine(imgmedicine,namemedicine,takemedicine,quentity);
                medicineList.add(medicine);
            }
            adapter = new MedicineAdapter(getActivity().getApplicationContext(),medicineList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
}
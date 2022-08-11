package com.example.ministry_of_health.ui.updatemedicine;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.Medicine;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.ui.medicine.MedicineAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class update_medicine_information extends Fragment {


    private RecyclerView recyclerView;
    private updatemedicineAdapter adapter;
    private List<Medicine> medicinesList;
    private String URL="http://"+ idinfo.getIpaddress()+"/health_app/allinformationofmedicine.php";
    private RequestQueue queue2;
    private EditText editsaerch;


    public static update_medicine_information newInstance() {
        return new update_medicine_information();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.update_medicine_information_fragment, container, false);

        editsaerch=view.findViewById(R.id.editsearch);
        recyclerView = view.findViewById(R.id.medicinwRV2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        queue2= Volley.newRequestQueue(getActivity().getApplicationContext());


        editsaerch.addTextChangedListener(new TextWatcher() {
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
            buildmedicinelist(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue2.add(request);
        return view;

    }
    private void buildmedicinelist(JSONObject response) {
        try {
            medicinesList = new ArrayList<Medicine>();
            JSONArray allmedicine=response.getJSONArray("medicine");
            for (int i=0;i<allmedicine.length();i++){
                JSONObject onemedicine=allmedicine.getJSONObject(i);
                String image=idinfo.getPathImg()+onemedicine.getString("image_medicine");
                String imgmedicine=image;
                String namemedicine=onemedicine.getString("name_medicine");
                String takemedicine=onemedicine.getString("take_medicine");
                int quentity=onemedicine.getInt("quantity");
                int quentity2=onemedicine.getInt("QuentityInStore");
                Medicine medicine=new Medicine(imgmedicine,namemedicine,takemedicine,quentity,quentity2);
                medicinesList.add(medicine);
            }
            adapter = new updatemedicineAdapter(getActivity().getApplicationContext(),medicinesList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
    private void filter(String text){
        ArrayList<Medicine> filteredlist=new ArrayList<>();
        for (Medicine item:medicinesList){
            if (item.getNamemedicine().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }
}
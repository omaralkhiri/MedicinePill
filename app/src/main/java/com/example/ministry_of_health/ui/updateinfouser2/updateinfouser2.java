package com.example.ministry_of_health.ui.updateinfouser2;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.ministry_of_health.ui.medicine.MedicineAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateinfouser2 extends Fragment {


    private TextView textViewname,textViewid;
    private ImageView addmedicine;
    private RecyclerView recyclerView;
    private Medicine2Adapter adapter;
    private List<Medicine> medicineList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String URL="http://"+idinfo.getIpaddress()+"/health_app/allmedicine.php";
    RequestQueue queue;
    String id;

    public static updateinfouser2 newInstance() {
        return new updateinfouser2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.upadateinfouser2_fragment, container, false);

        swipeRefreshLayout=view.findViewById(R.id.swiperfresh);
        textViewname=view.findViewById(R.id.TextName);
        textViewid=view.findViewById(R.id.TextID);
        addmedicine=view.findViewById(R.id.imageView26);
        textViewname.setText(idinfo.getUsername());
        textViewid.setText(idinfo.getIduser());
        id= textViewid.getText().toString();

        recyclerView = view.findViewById(R.id.listmedicine_RV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usingrequest();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

       usingrequest();

        addmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.updateinfouser3);
            }
        });
        return view;
    }


    private void buildmedicinelist(String response){
        try {
            medicineList = new ArrayList<Medicine>();
            JSONObject object=new JSONObject(response);
            JSONArray allmedicine=object.getJSONArray("medicine");
            for (int i=0;i<allmedicine.length();i++){
                JSONObject onemedicine=allmedicine.getJSONObject(i);
                int medicineid=onemedicine.getInt("medicine_id");
                idinfo.setMedicineid(medicineid);
                String image=idinfo.getPathImg()+onemedicine.getString("image_medicine");
                String imgmedicine=image;
                String namemedicine=onemedicine.getString("name_medicine");
                Medicine medicine=new Medicine(imgmedicine,namemedicine);
                medicineList.add(medicine);
            }
            adapter = new Medicine2Adapter(getActivity().getApplicationContext(),medicineList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
    public void usingrequest(){
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
    }
}
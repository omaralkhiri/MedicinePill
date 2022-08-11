package com.example.ministry_of_health.ui.UserOrder2;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class UserOrder2 extends Fragment {


    private RecyclerView recyclerView;
    private UserOrderAdapter2 adapter;
    private List<Medicine> medicineList;
    private RequestQueue queue;
    private String URL="http://"+ idinfo.getIpaddress()+"/health_app/medicinelistorder.php";
    private SwipeRefreshLayout swipeRefreshLayout;
    public static UserOrder2 newInstance() {
        return new UserOrder2();
    }

    @Override
    public void onStop() {
        super.onStop();
        queue.cancelAll("02");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.user_order2_fragment, container, false);
        ImageView addnewitem=view.findViewById(R.id.addnewitem);
        swipeRefreshLayout=view.findViewById(R.id.swiperfresh);
        Button complete=view.findViewById(R.id.button4);
        recyclerView = view.findViewById(R.id.medicinelistorder);
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
        addnewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.userOrder3);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URl="http://"+idinfo.getIpaddress()+"/health_app/order_completed.php";
                StringRequest request1=new StringRequest(Request.Method.POST, URl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      if (response.trim().equals("true")){
                          Toast.makeText(getActivity().getApplicationContext(), "Operation completed successfully", Toast.LENGTH_SHORT).show();
                      }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("id",idinfo.getIduser());
                        return params;
                    }

                };
                request1.setTag("02");
                queue.add(request1);
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
            adapter = new UserOrderAdapter2(getActivity().getApplicationContext(),medicineList);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_num",idinfo.getIduser());
                return params;
            }};

        queue.add(request);
    }
}
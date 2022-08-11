package com.example.ministry_of_health.ui.infoAdmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.Login_Activity;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Admin_information extends Fragment {


    private String url="http://"+idinfo.getIpaddress()+"/health_app/alluserinfo.php";
    private TextView textname,textidnumber,textcity,textbirthday,textgender;
    private Button btnsign;
    private RequestQueue queue;
    private String Id;
    private EditText textphone;
    public static Admin_information newInstance() {
        return new Admin_information();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.admin_information_fragment, container, false);

        textname=view.findViewById(R.id.tname);
        textbirthday=view.findViewById(R.id.textViewBirthday);
        textcity=view.findViewById(R.id.tplace);
        textidnumber=view.findViewById(R.id.tidnumber);
        textgender=view.findViewById(R.id.tgender);
        textphone=view.findViewById(R.id.editTextTextPersonName);
        btnsign=view.findViewById(R.id.bsignout);

        Id= idinfo.getId();
        Button update=view.findViewById(R.id.button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("UPDATE");
                alert.setCancelable(true);
                if (textphone.length()==10) {
                    alert.setMessage("YOU ARE SURE");
                    String URL1 = "http://"+idinfo.getIpaddress()+"/health_app/updatephonenumber.php";
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringRequest request = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();
                                    param.put("phone", textphone.getText().toString());
                                    param.put("id", textidnumber.getText().toString());
                                    return param;
                                }
                            };
                            queue.add(request);
                        }
                    });
                    alert.show();
                }else {
                    alert.setMessage("Please Enter correct number");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                }
            }
        });

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity().getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              getinformation(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_num",Id);
                return params;
            }
        };
        queue.add(request);
        return view;
    }
    private void getinformation(String response){
        try {
            JSONObject oneuser = new JSONObject(response);
            textname.setText(oneuser.getString("full_name"));
            textphone.setText(oneuser.getString("phone"));
            textgender.setText(oneuser.getString("gender"));
            textidnumber.setText(oneuser.getString("ID_number"));
            textcity.setText(oneuser.getString("governorate")+", "+oneuser.getString("location"));
            textbirthday.setText(oneuser.getString("birthday"));
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }



}
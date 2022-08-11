package com.example.ministry_of_health.ui.information;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

public class informationFragment extends Fragment {

    private RequestQueue queue;
    private String URL="http://"+idinfo.getIpaddress()+"/health_app/alluserinfo.php";
    private String ID_info;
    private TextView txtName,txtCity,txtbirthday,txtIDnum,txtGender;
    private EditText txtPHone;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root=inflater.inflate(R.layout.fragment_information,container,false);

        txtName=root.findViewById(R.id.txtname);
        txtCity=root.findViewById(R.id.txtcity);
        txtbirthday=root.findViewById(R.id.txtbirthday);
        txtIDnum=root.findViewById(R.id.txtidnumber);
        txtPHone=root.findViewById(R.id.editTextTextPersonName);
        txtGender=root.findViewById(R.id.txtgender);
        Button update =root.findViewById(R.id.button7);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("UPDATE");
                alert.setCancelable(true);
                if (txtPHone.length()==10) {
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
                                    param.put("phone", txtPHone.getText().toString());
                                    param.put("id", txtIDnum.getText().toString());
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

        ID_info= idinfo.getId();
        Button btnSignout=root.findViewById(R.id.btnsignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),Login_Activity.class);
                startActivity(intent);
            }
        });

        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             GETinformation(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_num",ID_info);
                return params;
            }
        };
        queue.add(request);

        return root;
    }

    private void GETinformation(String response){
        try {
            JSONObject user=new JSONObject(response);
            txtName.setText(user.getString("full_name"));
            txtIDnum.setText(user.getString("ID_number"));
            txtbirthday.setText(user.getString("birthday"));
            txtGender.setText(user.getString("gender"));
            txtCity.setText(user.getString("governorate")+", "+user.getString("location"));
            txtPHone.setText(user.getString("phone"));
            idinfo.setNeworder(user.getString("new_order"));
        }
        catch (Exception x){
            x.printStackTrace();
        }
    }
}
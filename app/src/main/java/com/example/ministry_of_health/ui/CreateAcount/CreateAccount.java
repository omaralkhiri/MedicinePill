package com.example.ministry_of_health.ui.CreateAcount;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccount extends Fragment {

    private EditText Editname,Editid,Editphone,Editpass;
    private RadioButton radiomale,radiofemale;
    private Spinner dayspinner,monthspinner,yearspinner,jobspinner;
    private String URL="http://"+ idinfo.getIpaddress()+"/health_app/signup.php";
    private String info;
    AlertDialog.Builder alert1;
    private String fullname,idnumber,phone,pass,job,gender,birthinfo;
    private RequestQueue queue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.create_account_fragment, container, false);

        alert1=new AlertDialog.Builder(getContext());
        alert1.setCancelable(true);

        queue= Volley.newRequestQueue(getActivity().getApplicationContext());

        Editname=view.findViewById(R.id.editName);
        Editid=view.findViewById(R.id.editIDnumber);
        Editphone=view.findViewById(R.id.editPhone);
        Editpass=view.findViewById(R.id.editPassword);
        radiomale=view.findViewById(R.id.radiomale);
        radiofemale=view.findViewById(R.id.radiofemale);
        dayspinner=view.findViewById(R.id.Dayspinner);
        monthspinner=view.findViewById(R.id.Monthspinner);
        yearspinner=view.findViewById(R.id.Yearspinner);
        jobspinner=view.findViewById(R.id.spinnerJob);



        Button btnsignup=view.findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname=Editname.getText().toString();
                idnumber=Editid.getText().toString();
                birthinfo+=dayspinner.getSelectedItem().toString()+"/";
                birthinfo+=monthspinner.getSelectedItem().toString()+"/";
                birthinfo+=yearspinner.getSelectedItem().toString();
                if (radiomale.isChecked())
                    gender="Male";
                else
                    gender="Female";
               phone=Editphone.getText().toString();
               job=jobspinner.getSelectedItem().toString();
               pass=Editpass.getText().toString();
              if (idnumber.length()==10&&gender.length()!=0&&phone.length()==10&&pass.length()!=0){
                  StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        if (response.trim().equals("true"))
                        {
                            alert1.setTitle("Create Account");
                            alert1.setMessage("New account created successfully");
                            alert1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Editname.setText("");
                                    Editid.setText("");
                                    Editphone.setText("");
                                    Editpass.setText("");
                                }
                            });
                            alert1.show();
                        }
                        else if(response.trim().equals("false")){
                            alert1.setTitle("Invalid sign up");
                            alert1.setMessage("Please verify that the data is correct");
                            alert1.show();
                        }

                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {

                      }
                  }){
                      @Override
                      protected Map<String, String> getParams() throws AuthFailureError {
                          Map<String, String> params = new HashMap<>();
                          params.put("fname", fullname);
                          params.put("idnum", idnumber);
                          params.put("birth", birthinfo);
                          params.put("gender", gender);
                          params.put("phone", phone);
                          params.put("branch_id",idinfo.getBranch_id());
                          params.put("pass", pass);
                          params.put("job",job);
                          return params;
                      }
                  };
                  queue.add(request);
              }else {
                  alert1.setTitle("Invalid sign up");
                  alert1.setMessage("Please verify that the data is correct");
                  alert1.show();
              }

            }
        });

        return view;
    }
}
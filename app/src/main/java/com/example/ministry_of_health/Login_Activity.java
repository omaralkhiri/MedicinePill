package com.example.ministry_of_health;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    private String URL="http://"+idinfo.getIpaddress()+"/health_app/login.php";
    private RequestQueue queue;
    private EditText idtxt;
    private EditText pass;
    private TextView txtsignup;
    private AlertDialog.Builder alert;
    private String job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idtxt=findViewById(R.id.idnumber);
        pass=findViewById(R.id.pass);
        txtsignup=findViewById(R.id.signup);
        queue=Volley.newRequestQueue(this);
        alert=new AlertDialog.Builder(this);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Signup_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void checklogin(View view) {
        String id=idtxt.getText().toString();
        String P=pass.getText().toString();
        idinfo.setId(id);
        StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                checkjob(response);

                if (job.trim().equals("Patient")) {
                    Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivityUser1.class);
                    startActivity(intent);
                    idtxt.setText("");
                    pass.setText("");
                }else if(job.trim().equals("Admin")) {
                    Intent intent2 = new Intent(getApplicationContext(), NavigationDrawerActivityAdmin.class);
                    startActivity(intent2);
                    idtxt.setText("");
                    pass.setText("");
                }else if(job.trim().equals("Pharmacist")){
                    Intent intent2 = new Intent(getApplicationContext(), NavigationDrawerActivityStore.class);
                    startActivity(intent2);
                    idtxt.setText("");
                    pass.setText("");
                } else if (job.trim().equals("Delivery")){
                    Intent intent2 = new Intent(getApplicationContext(), NavigationDrawerActivitydelivery.class);
                    startActivity(intent2);
                    idtxt.setText("");
                    pass.setText("");
                }
                else if (job.trim().equals("false")){
                    alert.setTitle("Invalid Login");
                    alert.setMessage("Invalid ID Number or password");
                    alert.setCancelable(true);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                    pass.setText("");
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
                params.put("user",id);
                params.put("pass",P);
                return params;
            }
        };
        request.setTag("loginreq");
        queue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        queue.cancelAll("loginreq");
    }
    private void checkjob(String respones){
        try {
                JSONObject jsonObject = new JSONObject(respones);
                job = jsonObject.getString("job");
                idinfo.setBranch_id(jsonObject.getString("branch_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
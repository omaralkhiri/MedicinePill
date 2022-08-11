package com.example.ministry_of_health;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import androidx.appcompat.app.AlertDialog;


public class Signup_Activity extends AppCompatActivity {
    EditText fullname,idnumber,phone,passworduser;
    Spinner placespinner,governorateSpinner,day,month,year;
    RadioButton maleradio,femaleradio;
    ImageView idcard;
    String URL="http://"+idinfo.getIpaddress()+"/health_app/accept_reject.php";
    String info;
    AlertDialog.Builder alert;
    private Bitmap bitmap;
    boolean status=false;
    String full, phone1, placeone,placetwo, gender, pass,id,job="Patient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        alert=new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setPositiveButton("OK",null);

        fullname = findViewById(R.id.full_name);
        idnumber = findViewById(R.id.id_number);
        day=findViewById(R.id.spinner);
        month=findViewById(R.id.spinner2);
        year=findViewById(R.id.spinner3);
        phone = findViewById(R.id.phone);
        placespinner = findViewById(R.id.place_spinner);
        maleradio = findViewById(R.id.radio_male);
        femaleradio = findViewById(R.id.radio_female);
        passworduser = findViewById(R.id.password);
        governorateSpinner=findViewById(R.id.governorate);
        idcard=findViewById(R.id.CivilIDphoto);

        full= phone1= placeone= gender=placetwo= pass=id="";

        List<String> place = new ArrayList<String>();
        place = new ArrayList<String>();
        place.add("Amman");
        place.add("Zarqa");
        place.add("Irbid");
        place.add("Aqaba");
        place.add("As-Salt");
        place.add("Madaba");
        place.add("al-Mafraq");
        place.add("Jerash");
        place.add("Ma'an");
        place.add("alTafila");
        place.add("al-Karak");
        place.add("Ajlun");
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,place);
        placespinner.setAdapter(adapter);
        idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent ,"select image"),100);
            }
        });

        placespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos==0){
                    List<String> governorate=new ArrayList<>();
                    governorate=new ArrayList<String>();
                    governorate.add("al-Quwaisima");
                    governorate.add("Wadi as-Sir");
                    governorate.add("Tila al-Ali");
                    governorate.add("Churaibat as-Suq");
                    governorate.add("al-Dschubaiha");
                    governorate.add("Ain al-Bascha");
                    governorate.add("Mardsch al-Hamam");
                    governorate.add("Askan Abu Nusair");
                    governorate.add("Na'ur");
                    governorate.add("Schafa Badran");
                    governorate.add("Sahab");
                    governorate.add("Suwailih");
                    governorate.add("Mars Mountain");
                    governorate.add("Crown Mountain");
                    governorate.add("Picnic Mountain");
                    ArrayAdapter<String> adapter1 = new
                            ArrayAdapter<String>(getApplication(),android.R.layout.simple_expandable_list_item_1,governorate);
                    governorateSpinner.setAdapter(adapter1);
                }else if (pos==1){
                    List<String> governorate=new ArrayList<>();
                    governorate=new ArrayList<String>();
                    governorate.add("al-Quwaisima");
                    governorate.add("Wadi as-Sir");
                    ArrayAdapter<String> adapter1 = new
                            ArrayAdapter<String>(getApplication(),android.R.layout.simple_expandable_list_item_1,governorate);
                    governorateSpinner.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    public void btnsignup(View view) {


        full = fullname.getText().toString().trim();
        id = idnumber.getText().toString().trim();
        info += day.getSelectedItem()+"/";
        info += month.getSelectedItem()+"/";
        info += year.getSelectedItem();
        phone1 = phone.getText().toString().trim();
        placeone = placespinner.getSelectedItem().toString();
        placetwo=governorateSpinner.getSelectedItem().toString();

        if (maleradio.isChecked())
            gender = "Male";
        else
            gender = "Female";
        pass = passworduser.getText().toString();
        if (id.length()==10&&phone.length()==10&&gender.length()!=0&&pass.length()!=0&&status==true) {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
            byte[]imageinByte=byteArrayOutputStream.toByteArray();
            String encoding= Base64.encodeToString(imageinByte,Base64.DEFAULT);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.trim().equals("true")) {
                        alert.setTitle("Sign Up");
                        alert.setMessage("Success Sign Up \nplease wait two days to check your Information");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                                startActivity(intent);
                            }
                        });
                        alert.show();
                        fullname.setText("");
                        idnumber.setText("");
                        phone.setText("");
                        passworduser.setText("");

                    }else if (response.trim().equals("false")){
                        alert.setTitle("Invalid sign up ");
                        alert.setMessage("Please verify that the data is correct");
                        alert.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("fname", full);
                    params.put("idnum", id);
                    params.put("birth", info);
                    params.put("gender", gender);
                    params.put("place1", placeone);
                    params.put("place2",placetwo);
                    params.put("phone", phone1);
                    params.put("pass", pass);
                    params.put("job",job);
                    params.put("idcard",encoding);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
        else
        {
            alert.setTitle("Invalid sign up");
            alert.setMessage("Please verify that the data is correct");
            alert.show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==100&&data !=null){
            Uri uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getApplication().getApplicationContext().getContentResolver(),uri);
                idcard.setImageBitmap(bitmap);
                status=true;
            }catch (Exception x){

            }
        }
    }
    
}
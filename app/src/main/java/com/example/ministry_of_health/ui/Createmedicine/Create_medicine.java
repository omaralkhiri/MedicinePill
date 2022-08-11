package com.example.ministry_of_health.ui.Createmedicine;

import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Create_medicine extends Fragment {
    private ImageView imageButton;
    private EditText medicinename, takemedicine, quentity, quentityinstore;
    private Button addmedicine;
    private RequestQueue queue;
    boolean status=false;
    private Bitmap bitmap;

    public static Create_medicine newInstance() {
        return new Create_medicine();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_medicine_fragment, container, false);

        imageButton = view.findViewById(R.id.imageView36);

        medicinename = view.findViewById(R.id.editTextTextmedicineName);
        takemedicine = view.findViewById(R.id.TAKE_MEDICINE);
        quentity = view.findViewById(R.id.editTextNumber);
        quentityinstore = view.findViewById(R.id.quentityST);
        addmedicine = view.findViewById(R.id.add);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("ADD MEDICINE");
        alert.setCancelable(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "select image"), 100);
            }
        });
        addmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, take, Quentity, QuentitySt;
                name = medicinename.getText().toString();
                take = takemedicine.getText().toString();
                Quentity = quentity.getText().toString();
                QuentitySt = quentityinstore.getText().toString();
                if (name.trim().equals("")||take.trim().equals("")||Quentity.trim().equals("")||QuentitySt.trim().equals("")||status==false) {
                    alert.setMessage("Please check information");
                    alert.setPositiveButton("OK",null);
                    alert.show();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] imageinByte = byteArrayOutputStream.toByteArray();
                    String encoding = Base64.encodeToString(imageinByte, Base64.DEFAULT);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url = "http://" + idinfo.getIpaddress() + "/health_app/CreateNewMedicine.php";
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.trim().equals("true"))
                                        Toast.makeText(getActivity().getApplicationContext(), "Successful Add", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("branchid", idinfo.getBranch_id());
                                    params.put("imagemedicine", encoding);
                                    params.put("medicinename", name);
                                    params.put("takemedicine", take);
                                    params.put("quentity", Quentity);
                                    params.put("quentityST", QuentitySt);
                                    return params;
                                }
                            };
                            queue.add(request);
                            medicinename.setText("");
                            takemedicine.setText("");
                            quentity.setText("");
                            quentityinstore.setText("");
                            imageButton.setImageResource(R.drawable.pills2);
                        }
                    });
                    alert.setMessage("Succesful Added");
                    alert.show();
                }
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100 && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageButton.setImageBitmap(bitmap);
                status=true;
            } catch (Exception x) {
                 x.printStackTrace();
            }


        }
    }
}
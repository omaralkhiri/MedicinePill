package com.example.ministry_of_health.ui.order;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ministry_of_health.R;
import com.example.ministry_of_health.databinding.FragmentOrderBinding;
import com.example.ministry_of_health.idinfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class orderFragment extends Fragment implements OnMapReadyCallback {

    private Button oredernow;
    private MapView mapView;
    private  double lat,lng;
    private RequestQueue queue;
    private String UR1="http://"+idinfo.getIpaddress()+"/health_app/add_lat_lng_and_add_order.php";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private AlertDialog.Builder alert;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        oredernow = view.findViewById(R.id.ordernow);
        mapView = view.findViewById(R.id.mapView);
        alert = new AlertDialog.Builder(getContext());
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        alert.setTitle("Notification");
        alert.setMessage("-Before placing an order, please be at home");
        alert.setPositiveButton("OK",null);
        alert.show();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        oredernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setTitle("Information about the Order");
                alert.setCancelable(true);
                alert.setPositiveButton("Yes",null);
                StringRequest request=new StringRequest(Request.Method.POST, UR1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     if (response.trim().equals("true")){
                         alert.setMessage("-Your medication order has been successfully processed.\n-We will contact you within a day or two");
                         alert.show();
                     }else{
                         alert.setMessage("-You cannot Order twice a month");
                         alert.show();
                     }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("userid", idinfo.getId());
                        params.put("lat",lat+"");
                        params.put("lng",lng+"");
                        return params;
                    }
                };
                queue.add(request);
            }
        });
        return view;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    try {
                        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        lat=addresses.get(0).getLatitude();
                        lng=addresses.get(0).getLongitude();
                        LatLng latlng = new LatLng(lat, lng);

                        googleMap.addMarker(new MarkerOptions().position(latlng).title("my Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    try {
                        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        LatLng latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                        googleMap.addMarker(new MarkerOptions().position(latlng).title("my Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
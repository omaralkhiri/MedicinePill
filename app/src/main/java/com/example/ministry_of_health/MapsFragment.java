package com.example.ministry_of_health;

import static com.example.ministry_of_health.R.id.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelKt;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsFragment extends Fragment {
    private double Lat=idinfo.getLatitude(),Log=idinfo.getLongitude();
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

                LatLng sydney = new LatLng(Lat, Log);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("USER"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
                Polyline polyline=googleMap.addPolyline(new PolylineOptions().add(new LatLng(Lat,Log),
                        new LatLng(37.4219534,-122.0861661)).color(Color.BLUE).geodesic(true));
        }

    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_maps, container, false);
        Button completed=view.findViewById(R.id.completed);
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL="http://"+idinfo.getIpaddress()+"/health_app/OrderCompleted.php";
                RequestQueue queue=Volley.newRequestQueue(getActivity().getApplicationContext());
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("ORDER");alert.setCancelable(false);
                StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      if (response.trim().equals("true")){
                          alert.setMessage("Order Completed");
                          alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {
                                  Navigation.findNavController(view).navigate(action_mapsFragment_to_orderlistdelivery);
                              }
                          });
                          alert.show();
                      }else {
                          alert.setMessage("Order not completed???");
                          alert.setPositiveButton("Yes",null);
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
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("idnumber",idinfo.getIduser());
                        return params;
                    }
                };
                queue.add(request);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }
}




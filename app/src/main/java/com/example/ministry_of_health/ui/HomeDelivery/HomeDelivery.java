package com.example.ministry_of_health.ui.HomeDelivery;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ministry_of_health.R;

public class HomeDelivery extends Fragment {

    public static HomeDelivery newInstance() {
        return new HomeDelivery();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.home_delivery_fragment, container, false);
        CardView cardViewmap=view.findViewById(R.id.cardmap);
        CardView cardVieworderlist=view.findViewById(R.id.cardorder);
        CardView cardViewinformation=view.findViewById(R.id.cardinformation);
        cardViewinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeDelivery_to_delivery_information);
            }
        });
        cardVieworderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeDelivery_to_orderlistdelivery);
            }
        });
        cardViewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
            }
        });
        return view;
    }
}
package com.example.ministry_of_health.ui.home;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ministry_of_health.R;
import com.example.ministry_of_health.idinfo;
import com.example.ministry_of_health.ui.information.informationFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    CardView cardView1,cardView2,cardView4;
    String  order;
    public static homeFragment newInstance() {
        return new homeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment, container, false);
        TextView text=view.findViewById(R.id.textView44);
        order=idinfo.getNeworder();
        text.setText("New Order in: "+order);
       cardView1=view.findViewById(R.id.settingcard);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_information);

            }
        });
         cardView2=view.findViewById(R.id.medicnecard);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_medicine);
            }
        });

        cardView4=view.findViewById(R.id.ordercard);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_order);
            }
        });
         return view;
    }






}
package com.example.ministry_of_health.ui.HomeStore;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ministry_of_health.R;

public class Home_store extends Fragment {

    public static Home_store newInstance() {
        return new Home_store();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_store_fragment, container, false);
        CardView cardView1=view.findViewById(R.id.informationcard);
        CardView cardView3=view.findViewById(R.id.orderlist);
        CardView cardView4=view.findViewById(R.id.updateuser);
        CardView cardView5=view.findViewById(R.id.informationcardx);
        CardView cardView6=view.findViewById(R.id.cardupdateuser);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_store_to_create_medicine);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_store_to_userOrder);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_store_to_update_medicine_to_user);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_store_to_informationStore);
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_store_to_updateuserinformation);
            }
        });

        return view;
    }
}
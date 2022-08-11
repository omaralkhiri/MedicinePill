package com.example.ministry_of_health.ui.HomeAdmin;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ministry_of_health.R;

public class HomeAdmin extends Fragment {

    private CardView cardinformation,cardcreate,cardaccept;
    public static HomeAdmin newInstance() {
        return new HomeAdmin();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_admin_fragment, container, false);
        cardinformation=view.findViewById(R.id.cardinformation);
        cardaccept=view.findViewById(R.id.cardaccept);
        cardcreate=view.findViewById(R.id.cardcreate);
        CardView cardremove=view.findViewById(R.id.cardreject);
        cardinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeAdmin_to_admin_information);
            }
        });
        cardcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeAdmin_to_create_accountFragment);
            }
        });
        cardaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeAdmin_to_accept_rejectFragment);
            }
        });
        cardremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeAdmin_to_removeAccount);
            }
        });
        return view;
    }


}
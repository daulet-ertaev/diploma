package com.example.diploma;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Settingsaccount extends Fragment {



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settingsaccount, container, false);

        // When click to back icon START
        Button btn_back = (Button) v.findViewById(R.id.back_to_profile);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();
            }
        });
        //When click to back icon END





        Button btn_changeUserData = (Button) v.findViewById(R.id.changepersonaldata);
        btn_changeUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr =  getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ChangeUserDataFragment());
                fr.commit();
            }
        });
        return v;
    }
}
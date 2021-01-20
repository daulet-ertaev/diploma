package com.example.diploma;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment{
    private Button logoutButton;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // When click to settings icon START
        Button btn_settings = (Button) v.findViewById(R.id.settings_profile);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Settingsaccount());
                fr.commit();
            }
        });
        //When click to settings icon END


        logoutButton = (Button)v.findViewById(R.id.logout);
        if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isAnonymous() == false) {
            logoutButton.setVisibility(View.VISIBLE);
        }
        else {
            logoutButton.setVisibility(View.GONE);
        }


        //logoutButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }


    // It doesnt work!!! But dont delete it!
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getActivity(),WelcomePage.class));
//                getActivity().finish();
//                break;
//        }
//    }
}
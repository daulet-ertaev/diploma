
package com.example.diploma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SuccessUploadProjectDataFragment extends Fragment {
    Button btn_next;
    public SuccessUploadProjectDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_success_upload_project_data, container, false);

        btn_next = v.findViewById(R.id.button_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr =  getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();
            }
        });

        return v;
    }
}
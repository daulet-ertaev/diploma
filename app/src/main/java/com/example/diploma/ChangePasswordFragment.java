package com.example.diploma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordFragment extends Fragment {
    Button btn_send, btn_back;
    EditText email_field;
    FirebaseAuth fAuth;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        btn_send = v.findViewById(R.id.SendButton);
        email_field = v.findViewById(R.id.email);
        btn_back = v.findViewById(R.id.back_icon);

        fAuth = FirebaseAuth.getInstance();
        if (!fAuth.getCurrentUser().isAnonymous()) {
            email_field.setText(fAuth.getCurrentUser().getEmail().toString());
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.sendPasswordResetEmail(email_field.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Password send to your email", Toast.LENGTH_LONG).show();
                                    back_to_back();
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }

        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_to_back();
            }
        });
        return v;
    }


    private void back_to_back() {
        if (!fAuth.getCurrentUser().isAnonymous()) {
            FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new ChangeUserDataFragment());
            fr.commit();
        } else {
            FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.container, new LoginFragment());
            fr.commit();
        }
    }
}
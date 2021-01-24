package com.example.diploma;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private EditText mEmail,mPassword;
    private Button mLoginBtn;
    private TextView mCreateBtn, forgotTextLink;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = v.findViewById(R.id.LoginEmail);
        mPassword = v.findViewById(R.id.LoginPassword);
        progressBar = v.findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = v.findViewById(R.id.SignInButton);
        mCreateBtn = v.findViewById(R.id.createText);
        forgotTextLink = v.findViewById(R.id.forgotPassword);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Error !", Toast.LENGTH_SHORT).show();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                            fr.replace(R.id.container, new ProfileFragment());
                            fr.commit();
                        }else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

             }
         });

        // Inflate the layout for this fragment
        return v;
    }
}
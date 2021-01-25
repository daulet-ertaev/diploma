package com.example.diploma;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class RegisterFragment extends Fragment {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    RadioButton male, female;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar progressBar;
    String replaced_email="";
    private static final String TAG = "Register";

    public RegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mFullName = v.findViewById(R.id.RegisterFullName);
        mEmail = v.findViewById(R.id.RegisterEmail);
        mPassword = v.findViewById(R.id.RegisterPassword);
        mPhone = v.findViewById(R.id.RegisterPhone);
        mRegisterBtn = v.findViewById(R.id.RegisterButton);
        mLoginBtn = v.findViewById(R.id.createText);
        male = v.findViewById(R.id.btn_male);
        female = v.findViewById(R.id.btn_female);

        fAuth = FirebaseAuth.getInstance();
        progressBar = v.findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);

                //SAVE USERDATA TO FIREBASE REALTIME: START
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");



                //Getting all value
                replaced_email = email.replace(".", ",");
                UserHelpWhenRegister helpclass = new UserHelpWhenRegister(replaced_email, fullName, password, phone);
                reference.child(replaced_email).setValue(helpclass);

                if (male.isChecked()){
                    reference.child(replaced_email).child("gender").setValue("male");
                }
                if(female.isChecked()){
                    reference.child(replaced_email).child("gender").setValue("female");
                }
                //SAVE USERDATA TO FIREBASE REALTIME: END


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //sent verif link
                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Verification Email has been sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Failure! Email not sent. " + e.getMessage());
                                }
                            });


                            Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                            fr.replace(R.id.container, new ProfileFragment());
                            fr.commit();
                        } else {
                            Toast.makeText(getActivity(), "Error " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new LoginFragment());
                fr.commit();
            }
        });
        return v;
    }
}
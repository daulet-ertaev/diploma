package com.example.diploma;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.RegisterFullName);
        mEmail      = findViewById(R.id.RegisterEmail);
        mPassword   = findViewById(R.id.RegisterPassword);
        mPhone      = findViewById(R.id.RegisterPhone);
        mRegisterBtn= findViewById(R.id.RegisterButton);
        mLoginBtn   = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final String email = mEmail.getText().toString().trim();
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

                                                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()){

                                                            //sent verif link
                                                            FirebaseUser user = fAuth.getCurrentUser();
                                                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(Register.this, "Verification Email has been sent.",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d(TAG, "Failure! Email not sent. "+e.getMessage());
                                                                }
                                                            });



                                                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent (getApplicationContext(), MainActivity.class));
                                                        }
                                                        else {
                                                            Toast.makeText(Register.this, "Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                            progressBar.setVisibility(View.GONE);
                                                        }

                                                    }
                                                });

                                            }
                                        }
        );
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }}

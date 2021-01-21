package com.example.diploma;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInDialog extends DialogFragment {
    private FirebaseAuth fAuth;
    private EditText emailText, passwordText;
    private TextView textView;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Login");
        builder.setView(view);

        emailText = view.findViewById(R.id.email);
        passwordText = view.findViewById(R.id.password);


        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//          LOG IN!
//                String email = emailText.getText().toString().trim();
//                String password = passwordText.getText().toString().trim();
//
//                if(TextUtils.isEmpty(email)){
//                    emailText.setError("Email is Required.");
//                    return;
//                }
//
//                if(TextUtils.isEmpty(password)){
//                    passwordText.setError("Password is Required.");
//                    return;
//                }
//
//                if(password.length() < 6){
//                    passwordText.setError("Password Must be >= 6 Characters");
//                    return;
//                }

                fAuth.signInWithEmailAndPassword("daulet.ertaev7@gmail.com","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        return builder.create();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Toast.makeText(getActivity(), "Dialog canceled", Toast.LENGTH_SHORT).show();
    }

}
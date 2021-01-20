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
        textView = view.findViewById(R.id.textView);


        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//          LOG IN!

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
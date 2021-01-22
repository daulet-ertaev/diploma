package com.example.diploma;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {
    private Button logoutButton, resendCode;
    private Dialog dialog;
    private FirebaseAuth fAuth;
    private TextView email_field, name_field, phone_field, verifyMsg;

    private static final String TAG = "MainActivity";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        fAuth = FirebaseAuth.getInstance();
        // When click to settings icon START
        Button btn_settings = (Button) v.findViewById(R.id.settings_profile);

        resendCode=v.findViewById(R.id.verifyButton);
        verifyMsg=v.findViewById(R.id.verificationText);

        email_field = (TextView)v.findViewById(R.id.email_info);
        name_field = (TextView)v.findViewById(R.id.fullname);
        phone_field = (TextView)v.findViewById(R.id.phone_number);

        //verification
        if (!fAuth.getCurrentUser().isAnonymous() && !fAuth.getCurrentUser().isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vi) {
                    //
                    fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Verification Email has been sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Failure! Email not sent. " + e.getMessage());
                        }
                    });
                }
            });
        }



        //user data
        if (FirebaseAuth.getInstance().getCurrentUser() !=null) {
            //email
            String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            email_field.setText(user_email);

            //name
            String user_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            name_field.setText(user_name);


            //phone
            String user_phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            phone_field.setText(user_phone);
        }


        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new Settingsaccount());
                fr.commit();
            }
        });
        //When click to settings icon END


        //logout button appearence
        //public void Appereance_logout() {
        logoutButton = (Button) v.findViewById(R.id.logout);
        if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isAnonymous() == false) {
            logoutButton.setVisibility(View.VISIBLE);
        } else {
            logoutButton.setVisibility(View.GONE);
        }
//        }

        //check if user logged in
        if (FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            String title = "You are not logged in!";
            String message = "Sign in or sign up";
            String button1String = "Sign in";
            String button2String = "Sign up";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title);  // заголовок
            builder.setMessage(message); // сообщение
            builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    LoginFragment nextFrag = new LoginFragment();
                    if (nextFrag != null) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();

                    }
                }
            });
            builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_LONG).show();
                    RegisterFragment nextFrag = new RegisterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, nextFrag, "findThisFragmen")
                                .addToBackStack(null)
                                .commit();

                }
            });
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
                fAuth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "You're logged anonymously", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(getActivity(), "Auth failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return v;
    }


}
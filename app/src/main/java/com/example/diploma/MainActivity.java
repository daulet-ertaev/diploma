package com.example.diploma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private Button resendCode;
    private TextView verifyMsg;
    private FirebaseAuth fAuth;
    private String userID;
    private static final String TAG = "MainActivity";

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        resendCode = findViewById(R.id.verifyButton);
        verifyMsg = findViewById(R.id.verificationText);


        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null) {
            fAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "You're logged anonymously", Toast.LENGTH_LONG).show();
                    } else {
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Auth failed", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }


            
        //NAVIGATION BAR START
        chipNavigationBar=findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i){
                    case R.id.home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.aaa:
                        fragment=new aaa();
                        break;
                    case R.id.notification:
                        fragment=new NotificaitonFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;
                }
                if(fragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
         });

        //NAVIGATION BAR ENDED
    }

    protected void onStart() {
        super.onStart();

        }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),WelcomePage.class));
        finish();
    }
}
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChangeUserDataFragment extends Fragment {
    EditText email_field,name_field,password_field,phone_field;
    RadioButton gender_male_field, gender_female_field;
    Button btn_update;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    private String decode_email="";
    private String undecode_email="";
    String replaced_email="";


    public ChangeUserDataFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_user_data, container, false);


        name_field = v.findViewById(R.id.RegisterFullName);
        email_field = v.findViewById(R.id.RegisterEmail);
        password_field = v.findViewById(R.id.RegisterPassword);
        phone_field = v.findViewById(R.id.RegisterPhone);
        gender_male_field = v.findViewById(R.id.btn_male);
        gender_female_field = v.findViewById(R.id.btn_female);
        btn_update = v.findViewById(R.id.UpdateButton);


        decode_email = fAuth.getInstance().getCurrentUser().getEmail().replace('.', ',');
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(decode_email);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //name
                String user_name = snapshot.child("registerFullName").getValue().toString();
                name_field.setText(user_name);

                //phone
                String user_phone = snapshot.child("registerPhone").getValue().toString();
                phone_field.setText(user_phone);

                //password
                String user_password = snapshot.child("registerPassword").getValue().toString();
                password_field.setText(user_password);

                String user_gender;
                user_gender = snapshot.child("gender").getValue().toString();
                if(user_gender.equals("female")){
                    gender_female_field.setChecked(true);
                }
                if(user_gender.equals("male")){
                    gender_male_field.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = name_field.getText().toString().trim();
                String user_phone = phone_field.getText().toString().trim();
                String user_password = password_field.getText().toString().trim();
                String user_gender = "";


                reference = FirebaseDatabase.getInstance().getReference().child("users");

                if(gender_male_field.isChecked()){
                    user_gender = "male";
                }
                if(gender_female_field.isChecked()) {
                    user_gender = "female";
                }
                UserHelpWhenRegister helpclass = new UserHelpWhenRegister(decode_email, user_name, user_password, user_phone, user_gender);
                reference.child(decode_email).setValue(helpclass);

                FragmentTransaction fr =  getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();


                Toast.makeText(getActivity(), "Your data successfully updated!", Toast.LENGTH_LONG).show();
            }

        });

        return v;
    }
}







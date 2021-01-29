package com.example.diploma;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateProjectFragment extends Fragment {
    private String[] categories = {"Sport", "IT"};
    private int count = 0;

    private Spinner spin_categories;
    private Button btn_next, btn_back, btn_choose_date, btn_rechoose;
    private EditText title, description, required_amount, date_end;
    private RadioGroup project_type;
    private RadioButton selected;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    List<String> projects_count = new ArrayList<String>();
    public CreateProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spin_categories.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_project, container, false);

        spin_categories = (Spinner)v.findViewById(R.id.categories);
        btn_next = (Button)v.findViewById(R.id.btn_next);
        btn_back = v.findViewById(R.id.back_to);
        btn_choose_date = v.findViewById(R.id.choose_data);
        btn_rechoose = v.findViewById(R.id.rechoose_data);
        title = (EditText)v.findViewById(R.id.title_of_project);
        description = (EditText)v.findViewById(R.id.description);
        required_amount = (EditText)v.findViewById(R.id.required_amount);
        project_type = (RadioGroup)v.findViewById(R.id.radioGroup);
        date_end = v.findViewById(R.id.Date);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("projects");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                count = count + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                String project_title, project_description, project_author, project_category;
                int project_required_amount;
                boolean project_isCharity = false;



                project_title = title.getText().toString().trim();
                project_description = description.getText().toString().trim();
                project_author = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                project_author = project_author.replace(".",",");
                String value = required_amount.getText().toString();

                if(TextUtils.isEmpty(project_title)){
                    title.setError("Title is required!");
                    return;
                }
                if(TextUtils.isEmpty(project_description)){
                    description.setError("Description is required");
                    return;
                }
                if(TextUtils.isEmpty(value)){
                    required_amount.setError("Amount is required");
                    return;
                }

                project_required_amount = Integer.parseInt(value);




                int id = project_type.getCheckedRadioButtonId();
                selected = (RadioButton)v.findViewById(id);
                String whichVar = selected.getText().toString();
                if(whichVar=="Charity"){
                    project_isCharity = true;
                }
                else if(whichVar=="Startup"){
                    project_isCharity = false;
                }

                Project_Create_Help_Class project = new Project_Create_Help_Class(project_title, project_description, project_required_amount,"Default" ,project_isCharity, project_author);
                ref.child(String.valueOf(count)).setValue(project);
                Toast.makeText(getActivity(),"Project created successfully",Toast.LENGTH_LONG).show();
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();
            }
        });



        btn_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_rechoose.setVisibility(View.VISIBLE);
                getDateForEnd();

                btn_choose_date.setVisibility(View.INVISIBLE);
            }
        });

        btn_rechoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateForEnd();
            }
        });




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();
            }
        });
        return v;
    }




    private void getDateForEnd() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDateSetListener = (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = day1 + "/" + month1 + "/" + year1;
            date_end.setText(date);
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mDateSetListener, year, month, day);


        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
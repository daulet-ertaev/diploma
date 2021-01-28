package com.example.diploma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.*;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.AEADBadTagException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProjectFragment extends Fragment {
    private String[] categories = {"Sport", "IT"};

    private Spinner spin_categories;
    private Button btn_next;
    private EditText title, description, required_amount;
    private RadioGroup project_type;
    private RadioButton selected;
    public CreateProjectFragment() {
        // Required empty public constructor
    }

    public static CreateProjectFragment newInstance(String param1, String param2) {
        CreateProjectFragment fragment = new CreateProjectFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        title = (EditText)v.findViewById(R.id.title_of_project);
        description = (EditText)v.findViewById(R.id.description);
        required_amount = (EditText)v.findViewById(R.id.required_amount);
        project_type = (RadioGroup)v.findViewById(R.id.radioGroup);



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

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("projects");
                Project project = new Project(project_title, project_description, project_required_amount,"Default" ,project_isCharity, project_author);
                ref.child(project_author).setValue(project);
                Toast.makeText(getContext(),"Project created successfully",Toast.LENGTH_LONG).show();
                FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ProfileFragment());
                fr.commit();

            }
        });




        return v;
    }
}
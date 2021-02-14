package com.example.diploma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddImagesFragment extends Fragment {
    private static final int IMAGE_CODE = 1;
    Button btn_choose, btn_upload;
    RecyclerView recyclerView;
    List<ModalAddImages> modalAddImagesList;
    AdapterAddImages adapterAddImages;
    List<String> ImageUris, ImageName;
    private int count = 0, totalitem=0;

    private StorageReference mStorageRef;

    public AddImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_images, container, false);

        btn_choose = v.findViewById(R.id.btn_chooseimage);
        btn_upload = v.findViewById(R.id.btn_uploadimage);
        btn_upload.setVisibility(View.INVISIBLE);
        recyclerView = v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        modalAddImagesList = new ArrayList<>();
        ImageUris = new ArrayList<>();
        ImageName = new ArrayList<>();



        //TO GET WHICH PROJECT: START
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("projects");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //TO GET WHICH PROJECT: END


        mStorageRef = FirebaseStorage.getInstance().getReference();




        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, IMAGE_CODE);
            }
        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.show();

                    for (int i = 0; i < totalitem; i++) {
                        StorageReference mRef = mStorageRef.child("projects").child(String.valueOf(count)).child(ImageName.get(i));
                        Uri myUri = Uri.parse(ImageUris.get(i));
                        mRef.putFile(myUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "FAIL" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                progressDialog.setMessage("Saved " + (int) progress + "%");
                            }
                        });
                    }

                    FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new SuccessUploadProjectDataFragment());
                    fr.commit();
                }

        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        modalAddImagesList.clear(); //WHEN ReCHOOSING LIST WILL BE CLEARED
        ImageUris.clear();
        ImageName.clear();

        if(requestCode == IMAGE_CODE  && resultCode == RESULT_OK){
            if(data.getClipData() != null ){ //MULTIPLE IMAGE
                totalitem = data.getClipData().getItemCount();
                for(int i=0; i<totalitem; i++){
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    String imagename = getFileName(imageUri);

                    ImageUris.add(String.valueOf(imageUri));
                    ImageName.add(imagename);


                    ModalAddImages modalAddImages = new ModalAddImages(imagename, imageUri);
                    modalAddImagesList.add(modalAddImages);

                    adapterAddImages = new AdapterAddImages(getActivity(), modalAddImagesList);
                    recyclerView.setAdapter(adapterAddImages);

                }

            }
            else if(data.getData() != null){ //ONE IMAGE
                Uri imageUri = data.getData();
                String imagename = getFileName(imageUri);
                totalitem = 1;

                ImageUris.add(String.valueOf(imageUri));
                ImageName.add(imagename);

                ModalAddImages modalAddImages = new ModalAddImages(imagename, imageUri);
                modalAddImagesList.add(modalAddImages);

                adapterAddImages = new AdapterAddImages(getActivity(), modalAddImagesList);
                recyclerView.setAdapter(adapterAddImages);
            }
            btn_upload.setVisibility(View.VISIBLE);
        }
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}



































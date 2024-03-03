package com.example.unitconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    private TextView textViewWelcome,textViewFullName,textViewEmail,textViewMobile;
    private ImageView profileImageView;
    private String fullName,email,mobile, userId;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;

    private ActivityResultLauncher<String> mGetContent;
    private Uri photoUri=null;

    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewWelcome = findViewById(R.id.show_welcome);
        textViewFullName = findViewById(R.id.show_fullName);
        textViewEmail = findViewById(R.id.show_email);
        textViewMobile = findViewById(R.id.show_mobile);
        profileImageView = findViewById(R.id.profile_image);

        buttonUpdate = findViewById(R.id.buttonUpdate);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                textViewMobile.setText(value.getString("Phone"));
                textViewFullName.setText(value.getString("Full Name"));
                textViewEmail.setText(value.getString("Email"));

                String url = value.getString("photo");
                textViewEmail.setText(value.getString("Email"));

                if(url != null) {
                    Glide.with(profile.this)
                            .load(url)
                            .into(profileImageView);
                }
            }
        });


        profileImageView.setOnClickListener(view -> mGetContent.launch("image/*"));

        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri = result;
                        Glide.with(this)
                                .load(photoUri)
                                .into(profileImageView);
                    }
                });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = String.valueOf( textViewMobile.getText() );
                String name = String.valueOf( textViewFullName.getText() );
                String email = String.valueOf( textViewEmail.getText() );

                if(photoUri == null){
                    updateData(phone,name,email,null);
                }
                else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String uid = auth.getUid();
                    if(uid == null) return;

                    StorageReference ref = FirebaseStorage.getInstance().getReference().child("profile").child(uid);
                    ref.putFile(photoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){

                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = String.valueOf(uri);
                                        updateData(phone,name,email,url);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else{
                                Toast.makeText(profile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    private void updateData(String phone, String name, String email, String url){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        if(uid == null) return;


        DocumentReference documentReference = fStore.collection("users").document(uid);
        Map<String,Object> user = new HashMap<>();
        user.put("Full Name",name);
        user.put("Email",email);
        user.put("Phone",phone);
        user.put("photo",url);
        documentReference.set(user).addOnSuccessListener(unused -> Toast.makeText(profile.this, "Updated", Toast.LENGTH_SHORT).show());

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

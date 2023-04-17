package com.example.application;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfile extends AppCompatActivity {
    private EditText Name, Email, Phone, Password;
    private Button btnUpdate;
    private CircleImageView profilePic;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;
    String userID;
    private ImageView backButton;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Name = findViewById(R.id.edittext_name);
        Email = findViewById(R.id.edittext_email);
        Phone = findViewById(R.id.edittext_phone);
        Password = findViewById(R.id.edittext_password);
        btnUpdate = findViewById(R.id.btn_update);
        profilePic = findViewById(R.id.editPicProfile);
        backButton = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        String name = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        Name.setText(name);
        Email.setText(email);
        Phone.setText(phone);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        storageReference = FirebaseStorage.getInstance().getReference("profile_pictures");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String password = snapshot.child("password").getValue().toString();
                Password.setText(password);

                if (snapshot.hasChild("profile_picture")) {
                    String profilePictureUrl = snapshot.child("profile_picture").getValue().toString();
                    Glide.with(getApplicationContext()).load(profilePictureUrl).into(profilePic);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, "Failed to load profile picture.", Toast.LENGTH_SHORT).show();
            }

        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ViewProfile.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String phone = Phone.getText().toString();
                String password = Password.getText().toString();

                reference.child("userName").setValue(name);
                reference.child("email").setValue(email);
                reference.child("phoneNumber").setValue(phone);
                reference.child("password").setValue(password);

                if (imageUri != null) {
                    final StorageReference fileRef = storageReference.child(userID + "." + getFileExtension(imageUri));
                    fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    reference.child("profile_picture").setValue(uri.toString());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Failed to upload profile picture.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                // Example: Display the updated values in a Toast
                String updatedInfo = "Updated";
                Toast.makeText(EditProfile.this, updatedInfo, Toast.LENGTH_LONG).show();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(getApplicationContext()).load(imageUri).into(profilePic);
        }
    }
}

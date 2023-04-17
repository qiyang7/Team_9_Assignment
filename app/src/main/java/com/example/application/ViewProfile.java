package com.example.application;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfile extends AppCompatActivity {
    private TextView usernameView;
    private TextView phoneView;
    private TextView emailView;
    private FirebaseAuth authProfile;
    private DatabaseReference reference;
    private ImageView backbutton;
    private CircleImageView profilePic;

    private String userID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);

        phoneView = findViewById(R.id.profilePhoneNumber);
        usernameView = findViewById(R.id.profileUserName);
        emailView = findViewById(R.id.profileEmail);
        profilePic = findViewById(R.id.profile_picture);

        Button editProf = findViewById(R.id.buttonEditProf);
        backbutton = findViewById(R.id.buttonBack);
        Button invite = findViewById(R.id.buttonInviteFriend);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteIntent = new Intent();
                inviteIntent.setAction(Intent.ACTION_SEND);
                inviteIntent.putExtra(Intent.EXTRA_TEXT, "I've just heard about this Dementia Application, try it out! " +
                        "https://play.google.com/store/apps/details?id=com.mugshotgames.slidingseas&hl=en");
                inviteIntent.setType("text/plain");
                startActivity(Intent.createChooser(inviteIntent, "Invite friends"));
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this,NewHompage.class);
                startActivity(intent);
            }
        });



        authProfile = FirebaseAuth.getInstance();
        userID = authProfile.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("userName").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String phoneNumber = snapshot.child("phoneNumber").getValue().toString();
                String password = snapshot.child("password").getValue().toString();
                if (snapshot.hasChild("profile_picture")) {
                    String profilePictureUrl = snapshot.child("profile_picture").getValue().toString();
                    Glide.with(getApplicationContext()).load(profilePictureUrl).into(profilePic);
                }
                usernameView.setText(username);

                emailView.setText(email);
                phoneView.setText(phoneNumber);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, EditProfile.class);
                intent.putExtra("username",usernameView.getText().toString());
                intent.putExtra("email",emailView.getText().toString());
                intent.putExtra("phone",phoneView.getText().toString());
                startActivity(intent);
            }
        });


        }







    }


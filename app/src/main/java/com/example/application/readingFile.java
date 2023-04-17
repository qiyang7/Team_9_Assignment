package com.example.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class readingFile extends AppCompatActivity {
    TextView title,body,readingProg;
    private ScrollView scrollView;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Handler handler;
    private Runnable runnable;
    Button startQuiz;
    private ViewTreeObserver.OnScrollChangedListener scrollListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.symptomreadingpage);
        title = findViewById(R.id.titleReading);
        body = findViewById(R.id.readingText);
        scrollView = findViewById(R.id.readingScrollView);
        readingProg = findViewById(R.id.readingProgress);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        String topic = getIntent().getStringExtra("Topic");
        String description = getIntent().getStringExtra("Description");
        title.setText(topic);
        body.setText(description);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobile-assignment-dc649-default-rtdb.firebaseio.com/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child(topic).getChildren()) {
                    final String getTitle = dataSnapshot.child("Title").getValue(String.class);
                    final String getDescription = dataSnapshot.child("Description").getValue(String.class);


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseApp.initializeApp(this);
        reference= FirebaseDatabase.getInstance().getReference();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                int height = scrollView.getChildAt(0).getHeight();
                int scrollViewHeight = scrollView.getHeight();
                int progress = (int) ((float) scrollY / (height - scrollViewHeight) * 100);

                if (progress < 100) {
                    reference.child("Report").child("topics").child(topic).child(user.getUid()).child("progress").setValue(progress);
                } else {
                    //
                    reference.child("Report").child("topics").child(topic).child(user.getUid()).child("progress").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int progress = dataSnapshot.getValue(Integer.class);
                            if (progress == 100) {
                                scrollView.getViewTreeObserver().removeOnScrollChangedListener(scrollListener);
                                return;
                            } else {
                                reference.child("Report").child(topic).child(user.getUid()).child("progress").setValue(100);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

        });



        handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        reference.child("Report").child("topics").child(topic).child(user.getUid()).child("progress").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                String progress = dataSnapshot.getValue(String.class);
//                                readingProg.setText("Progress: " + String.valueOf(progress) + "%");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        handler.postDelayed(this, 5000);
                    }
                };

                // Start the runnable to update the progress every few seconds
        handler.post(runnable);

};

    }



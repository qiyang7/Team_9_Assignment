package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    public static List<TestModel> testModels= new ArrayList<>();
    private TestAdapter testAdapter;

    ProgressBar progressBar;
    private ImageView backBtn;
    private FirebaseUser currentUser;
    private DatabaseReference ref;
    private int progress;
    private int progressPercentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        recyclerView = findViewById(R.id.testRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        testModels = new ArrayList<>();
        testAdapter = new TestAdapter(TestActivity.this, testModels, TestActivity.this);
        recyclerView.setAdapter(testAdapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            ref = FirebaseDatabase.getInstance().getReference("QuizScore");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    testModels.clear();
                    DataSnapshot topicsSnapshot = snapshot.child("topics");
                    for (DataSnapshot topicSnapshot : topicsSnapshot.getChildren()) {
                        String topic = topicSnapshot.getKey();
                        if(topicSnapshot.child(currentUser.getUid()).child("scoreQuiz").getValue(Integer.class) != null) {
                            progress = topicSnapshot.child(currentUser.getUid()).child("scoreQuiz").getValue(Integer.class);

                        }else {

                        }
                        TestModel model = new TestModel(topic, progress);
                        testModels.add(model);


                    }
                    testAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            backBtn = findViewById(R.id.backButtonQuizList);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TestActivity.this, NewHompage.class);
                    startActivity(intent);
                }
            });


//                readingCornerModels = new ArrayList<>();
//
//                readingCornerModels.add(new ReadingCornerModel("Symptoms of Dementia", "hello this is ", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Dealing with Dementia", "hello Dealing w Demen", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Caregivers guide to dementia", "bruh", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Preventing Dementia", "what", R.drawable.symptoms));


        }
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(TestActivity.this, Quiz.class);

        intent.putExtra("Quiz", testModels.get(position).getTopicQuiz());
        Toast.makeText(this, testModels.get(position).getTopicQuiz(), Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }


}







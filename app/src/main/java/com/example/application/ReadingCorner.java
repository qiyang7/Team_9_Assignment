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
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

        public class ReadingCorner extends AppCompatActivity implements RecyclerViewInterface {

            private RecyclerView recyclerView;
            public static List<ReadingCornerModel> readingCornerModels= new ArrayList<>();
            private ReadingCornerAdapter readingCornerAdapter;
            private SearchView searchView;

            private ImageView backBtn;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.readingcorner_new);

                readingCornerModels = new ArrayList<>();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Topics");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        readingCornerModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            ReadingCornerModel model = dataSnapshot.getValue(ReadingCornerModel.class);
                            readingCornerModels.add(model);
                        }
                        readingCornerAdapter = new ReadingCornerAdapter(ReadingCorner.this, readingCornerModels, ReadingCorner.this);
                        recyclerView.setAdapter(readingCornerAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                backBtn = findViewById(R.id.buttonBack);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ReadingCorner.this,NewHompage.class);
                        startActivity(intent);
                    }
                });
                searchView = findViewById(R.id.searchView);
                searchView.clearFocus();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterList(newText);
                        return false;
                    }
                });
                recyclerView = findViewById(R.id.recyclerViewReading);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);

//                readingCornerModels = new ArrayList<>();
//
//                readingCornerModels.add(new ReadingCornerModel("Symptoms of Dementia", "hello this is ", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Dealing with Dementia", "hello Dealing w Demen", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Caregivers guide to dementia", "bruh", R.drawable.symptoms));
//                readingCornerModels.add(new ReadingCornerModel("Preventing Dementia", "what", R.drawable.symptoms));


            }

            private void filterList(String newText) {
                List<ReadingCornerModel> filteredList = new ArrayList<>();
                for (ReadingCornerModel readingCorner : readingCornerModels) {
                    if (readingCorner.getTopicName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(readingCorner);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
                } else {
                    readingCornerAdapter.setFilteredList(filteredList);
                }

            }


            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ReadingCorner.this, readingFile.class);

                intent.putExtra("Topic", readingCornerModels.get(position).getTopicName());
                intent.putExtra("Description", readingCornerModels.get(position).getDescription());
                startActivity(intent);

            }


        }







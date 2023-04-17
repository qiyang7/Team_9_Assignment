package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.color.utilities.Score;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<TestModel> testModels;

    private DatabaseReference myRef;
    private ProgressBar progressBar;

    public TestAdapter(Context context, List<TestModel> testModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.testModels = testModels;
        this.recyclerViewInterface = recyclerViewInterface;

    }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.test_item_layout, parent, false);
            return new ViewHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder (@NonNull ViewHolder holder,int position){
            TestModel testModel = testModels.get(holder.getAdapterPosition());
            holder.topicName.setText(testModel.getTopicQuiz());
            holder.progressBar.setMax(5);
            holder.progressBar.setProgress(testModel.getScoreQuiz());
            holder.score.setText("Top Score: " + String.valueOf(testModel.getScoreQuiz()) + "/5");




        }

        @Override
        public int getItemCount () {
            return testModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView topicName;
            TextView score;


            ProgressBar progressBar;

            public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);

                topicName = itemView.findViewById(R.id.testTitle);
                score = itemView.findViewById(R.id.ProgressScore);
                progressBar = itemView.findViewById(R.id.ProgressBar);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (recyclerViewInterface != null) {
                            TestModel testModel = testModels.get(getAdapterPosition());
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemClick(pos);
                            }
                        }
                    }

                    ;
                });
            }
        }
    }



//        @Override
//        public void onClick(View v) {
//            if (recyclerViewInterface != null) {
//                ReadingCornerModel readingCornerModel = readingCornerModels.get(getAdapterPosition());
//                int pos = getAdapterPosition();
//                Toast.makeText(context, readingCornerModel.getTopicName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, readingFile.class);
//                intent.putExtra("title", readingCornerModel.getTopicName());
//                context.startActivity(intent);




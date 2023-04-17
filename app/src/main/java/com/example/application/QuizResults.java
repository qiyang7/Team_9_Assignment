package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class QuizResults extends AppCompatActivity {

    int previousScore;
    int score;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_score);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("QuizScore");
        final AppCompatButton newQuizButton = findViewById(R.id.newQuizBtn);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);
        final TextView wrongAnswer = findViewById(R.id.wrongAnswers);
        final TextView unansweredQuestion = findViewById(R.id.unansweredQues);

        final int getCorrectAnswers = getIntent().getIntExtra("correct",0);
        final int getIncorrectAnswers = getIntent().getIntExtra("incorrect",0);
        final int getUnansweredQuestions = getIntent().getIntExtra("unanswered",0);

        correctAnswer.setText("Correct Answers: " + String.valueOf(getCorrectAnswers));
        wrongAnswer.setText("Incorrect Answers: " + String.valueOf(getIncorrectAnswers));
        unansweredQuestion.setText("Unanswered Questions: " + String.valueOf(getUnansweredQuestions));

        final String getSelectedTopic = getIntent().getStringExtra("QuizTopic");

        reference.child("topics").child(getSelectedTopic).child(user.getUid()).child("scoreQuiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(Integer.class) == null) {
                    previousScore = 0;
                }else {
                    previousScore = snapshot.getValue(Integer.class);
                }
                if (getCorrectAnswers > previousScore){
                    reference.child("topics").child(getSelectedTopic).child(user.getUid()).child("scoreQuiz").setValue(getCorrectAnswers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizResults.this, "Score saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(QuizResults.this, "Score failed to be saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this, TestActivity.class));
                getIntent().putExtra("correctAns",String.valueOf(correctAnswer));
                getIntent().putExtra("incorrectAns",String.valueOf(wrongAnswer));
                getIntent().putExtra("quizDone",String.valueOf(getSelectedTopic));
                finish();
            }
        });

    }
}

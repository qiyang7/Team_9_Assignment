package com.example.application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;


    //    @Override
////    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(),NewHompage.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final EditText password = (EditText) findViewById(R.id.editPassword);
        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = String.valueOf(email.getText());
                String passwordLogin = String.valueOf(password.getText());
                if(TextUtils.isEmpty(emailLogin)){
                    Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordLogin)){
                    Toast.makeText(MainActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),NewHompage.class);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
            });
        TextView registerAcc = (TextView) findViewById(R.id.register);
        registerAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        TextView forgotPass = (TextView) findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
            }

//        }
//        );
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//        @Override public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will // automatically handle clicks on the Home/Up button, so long // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId(); //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//    }
    }


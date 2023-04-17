package com.example.application;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth= FirebaseAuth.getInstance();
        final EditText RegEmail = (EditText) findViewById(R.id.editRegisterEmail);
        final EditText Username = (EditText) findViewById(R.id.editUsername);
        final EditText Phone = (EditText) findViewById(R.id.editPhoneNumber);
        final EditText RegisterPassword = (EditText) findViewById(R.id.editRegisterPassword);
        final EditText confirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        Button register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();

                String email1 = String.valueOf(RegEmail.getText());
                String username = String.valueOf(Username.getText());
                String phoneNo = String.valueOf(Phone.getText());
                String password1 = String.valueOf(RegisterPassword.getText());
                String confirmpass1 = String.valueOf(confirmPassword.getText());
                if(TextUtils.isEmpty(email1)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNo)) {
                    Toast.makeText(Register.this, "Enter Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(RegisterPassword.getText().toString().equals(confirmPassword.getText().toString())){

                    reference = database.getReference("Users");
                    mAuth.createUserWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User users = new User(username,email1, phoneNo, password1);
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()) .setValue(users);
                                        Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Register.this, "Password is not the same", Toast.LENGTH_SHORT).show();
                }
            }


    });
        TextView oldAcc = (TextView) findViewById(R.id.loginOld);
        oldAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
}
});

    }
}
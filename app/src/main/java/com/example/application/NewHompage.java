package com.example.application;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.application.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewHompage extends AppCompatActivity {


    private static final String CHANNEL_ID = "login_notification_channel";
    private static final String CHANNEL_NAME = "Login Notification Channel";
    private static final String CHANNEL_DESC = "Reminds the user to log in every 10 seconds";

    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;
    private Handler handler;

    FirebaseAuth auth;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_homepage);
        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(NewHompage.this,MainActivity.class);
            startActivity(intent);
        }
        CardView readingCorner = findViewById(R.id.cardReadingCorner);

        readingCorner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NewHompage.this, ReadingCorner.class);
                startActivity(intent);
            }
        });

        CardView quiz = findViewById(R.id.cardQuiz);
        quiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NewHompage.this, TestActivity.class);
                startActivity(intent);

            }
        });

                CardView cert = findViewById(R.id.cardCert);
        cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewHompage.this,Certificate.class);
                startActivity(intent);
            }
        });
        CardView profile = findViewById(R.id.cardProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewHompage.this,ViewProfile.class);
                startActivity(intent);
            }
        });
        CardView analytics = findViewById(R.id.cardAnalytics);
        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewHompage.this,ReportActivity.class);
                startActivity(intent);
            }
        });
        CardView logout = findViewById(R.id.cardLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(NewHompage.this,MainActivity.class);
                startActivity(intent);
            }
        });
        createNotificationChannel();
        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Make sure to log in to complete your daily tasks!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAppInForeground(NewHompage.this)) {
                    sendNotification(notificationManager);
                }
                handler.postDelayed(this, 30000); // 10 seconds delay
            }
        }, 0);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(NotificationManagerCompat notificationManager) {
        notificationManager.notify(1, builder.build());
    }

    private boolean isAppInForeground(Context context) {
        android.app.ActivityManager.RunningAppProcessInfo appProcessInfo = new android.app.ActivityManager.RunningAppProcessInfo();
        android.app.ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND);
    }

}

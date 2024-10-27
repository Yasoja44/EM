package com.example.test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddReview extends AppCompatActivity {

    private static final String CHANNEL_ID = "1010";
    RatingBar ratingBar;
    EditText reviewDesc, reviewRating;
    Button addReviewBtn;
    FirebaseFirestore fStory;
    FirebaseAuth fAuth;
    private String movieId, movieName;
    FloatingActionButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        ratingBar = findViewById(R.id.rating_bar);
        reviewDesc = findViewById(R.id.review_desc);
        //reviewRating = findViewById(R.id.rating_bar);
        addReviewBtn = findViewById(R.id.add_review_button);
        fStory = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        movieId = getIntent().getStringExtra("movieId");
        movieName = getIntent().getStringExtra("movieName");
        logoutBtn = findViewById(R.id.float_logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AddReview.this, Login.class);
                startActivity(intent);
            }
        });

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addReviewBtn.setText("Rating:" + ratingBar.getRating());

                CollectionReference df = fStory.collection("Reviews");
                Map<String, Object> reviewInfo = new HashMap<>();
                FirebaseUser user = fAuth.getCurrentUser();
                reviewInfo.put("UserId", user.getUid());
                //Toast.makeText(AddReview.this, user.getUid(), Toast.LENGTH_SHORT).show();
                reviewInfo.put("MovieId", movieId);
                reviewInfo.put("ReviewDesc", reviewDesc.getText().toString());
                reviewInfo.put("ReviewRating", ratingBar.getRating());
                df.add(reviewInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddReview.this, "Review Added", Toast.LENGTH_SHORT).show();
                        sendNotification(movieName);
                        Intent intent = new Intent(AddReview.this, ViewAllMoviesUser.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddReview.this, "Review Not Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void sendNotification(String Name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Review")
                .setContentText("Review Added for Movie : " + Name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, AddReview.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = manager.getNotificationChannel(CHANNEL_ID);
            if (notificationChannel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(CHANNEL_ID, "Description", importance);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                manager.createNotificationChannel(notificationChannel);
            }
        }


        manager.notify(0,builder.build());

    }


}
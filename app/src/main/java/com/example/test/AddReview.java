package com.example.test;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddReview extends AppCompatActivity {

    RatingBar ratingBar;
    EditText reviewDesc, reviewRating;
    Button addReviewBtn;
    FirebaseFirestore fStory;
    FirebaseAuth fAuth;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ratingBar = findViewById(R.id.rating_bar);
        reviewDesc = findViewById(R.id.review_desc);
        //reviewRating = findViewById(R.id.rating_bar);
        addReviewBtn = findViewById(R.id.add_review_button);
        fStory = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        movieId = getIntent().getStringExtra("movieId");

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
}
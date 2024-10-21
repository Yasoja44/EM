package com.example.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class ViewReview extends AppCompatActivity {

    private ImageView movieImage;
    private TextView movieName, reviewerName, reviewDesc;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        movieImage = findViewById(R.id.movie_view_Image);
        //movieName = findViewById(R.id.movie_view_name);
        reviewerName = findViewById(R.id.reviewer_name);
        reviewDesc = findViewById(R.id.review_desc);
        ratingBar = findViewById(R.id.rating_movie);


        //movieName.setText(getIntent().getStringExtra("reviewMovieId"));
        reviewerName.setText(getIntent().getStringExtra("reviewUserId"));
        reviewDesc.setText(getIntent().getStringExtra("reviewDesc"));
        Toast.makeText(this, String.valueOf(Float.parseFloat(getIntent().getStringExtra("reviewRating"))), Toast.LENGTH_SHORT).show();
        ratingBar.setRating( Float.parseFloat(getIntent().getStringExtra("reviewRating")) );


        //ratingBar.setRating((float) (totalRating/ratingCount));

        String reviewPic = getIntent().getStringExtra("reviewPic");

        Picasso.get().load(reviewPic).into(movieImage);
    }
}
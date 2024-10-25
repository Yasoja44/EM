package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.Movie;
import com.example.test.Model.Review;
import com.example.test.ViewHolder.MovieAllViewHolder;
import com.example.test.ViewHolder.ReviewAllViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllReviews extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Review> reviewAllList;
    private ReviewAllViewHolder reviewViewHolder;
    private TextView textView;
    private EditText search;
    private String movieId, moviePic, movieName;
    private RatingBar ratingBar;
    FloatingActionButton addReviewBtn, logoutBtn;
    Map<String, String> usersMap;
    double ratingCount, totalRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_reviews);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        search=findViewById(R.id.search_reviews_all);
        recyclerView=findViewById(R.id.card_recycleView_reviews_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.search_reviews_all);
        addReviewBtn = findViewById(R.id.float_add_review);
        ratingBar = findViewById(R.id.rating_total);
        usersMap = new HashMap<>();
        logoutBtn = findViewById(R.id.float_logout);
        textView = findViewById(R.id.review_view_all);

        reviewAllList=new ArrayList<>();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ViewAllReviews.this, Login.class);
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    reviewViewHolder.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        movieId = getIntent().getStringExtra("movieId");
        moviePic = getIntent().getStringExtra("moviePic");
        movieName  = getIntent().getStringExtra("movieName");

        textView.setText(movieName + ": Reviews");

        firestore.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> list= task.getResult().getDocuments();
                        for(DocumentSnapshot ds:list){

                            String ReviewUserId=ds.getId();
                            String ReviewUsername=ds.getString("FirstName");
                            //Toast.makeText(ReviewAllViewHolder.this, ReviewUserId, Toast.LENGTH_SHORT).show();

                            usersMap.put(ReviewUserId, ReviewUsername);
                        }
                        firestore.collection("Reviews").get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {




                                        ratingCount = 0;
                                        totalRating = 0;
                                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                        for(DocumentSnapshot ds:list){
                                            if (movieId.equals(ds.getString("MovieId"))){
                                                String id =ds.getId();
                                                String ReviewUser=usersMap.get(ds.getString("UserId"));
                                                String ReviewMovie=ds.getString("MovieId");
                                                String ReviewDesc=ds.getString("ReviewDesc");
                                                double ReviewRating=ds.getDouble("ReviewRating");
                                                String ReviewPic=moviePic;

                                                ratingCount += 1;
                                                totalRating += ReviewRating;
                                                Review review = new Review(id,ReviewUser,ReviewMovie,ReviewDesc,ReviewRating,ReviewPic);
                                                reviewAllList.add(review);
                                            }


                                        }
                                        ratingBar.setRating((float) (totalRating/ratingCount));
                                        reviewViewHolder = new ReviewAllViewHolder(ViewAllReviews.this, reviewAllList);
                                        recyclerView.setAdapter(reviewViewHolder);
                                    }
                                });

                    }
                });



        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllReviews.this, AddReview.class);
                intent.putExtra("movieId", movieId);
                intent.putExtra("movieName", movieName);
                startActivity(intent);
            }
        });
    }
}
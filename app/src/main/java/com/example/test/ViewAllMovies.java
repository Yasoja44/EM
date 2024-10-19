package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.Movie;
import com.example.test.ViewHolder.MovieAllViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllMovies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Movie> movieAllList;
    private MovieAllViewHolder movieViewHolder;
    private TextView textView;
    private EditText search;
    FloatingActionButton addMovieBtn,logoutBtn;
    FirebaseAuth fAuth;
    Map<String, String> usersMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_movies);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        search=findViewById(R.id.search_movies_all);
        recyclerView=findViewById(R.id.card_recycleView_movies_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.search_movies_all);
        addMovieBtn = findViewById(R.id.float_add_movie);
        fAuth = FirebaseAuth.getInstance();
        usersMap = new HashMap<>();
        logoutBtn = findViewById(R.id.float_logout);

        movieAllList=new ArrayList<>();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ViewAllMovies.this, Login.class);
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
                    movieViewHolder.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FirebaseUser currentUser = fAuth.getCurrentUser();
        firestore.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> list= task.getResult().getDocuments();
                        for(DocumentSnapshot ds:list){

                            String userId=ds.getId();
                            String isUsername=ds.getString("isUser");
                            //Toast.makeText(ReviewAllViewHolder.this, ReviewUserId, Toast.LENGTH_SHORT).show();

                            usersMap.put(userId, isUsername);
                        }
                        if (usersMap.get(currentUser.getUid()).equals("1")){

                            addMovieBtn.setVisibility(View.VISIBLE);
                        }else{
                             addMovieBtn.setVisibility(View.INVISIBLE);
                        }
                    }
                });


        //Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();


        firestore.collection("Movies").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:list){
                            String id =ds.getId();
                            String movieName=ds.getString("MovieName");
                            String MovieDesc=ds.getString("MovieDesc");
                            String MovieReleaseDate=ds.getString("MovieReleaseDate");
                            String MovieGenre=ds.getString("MovieGenre");
                            String MoviePic=ds.getString("MoviePic");

                            Movie movie = new Movie(id,movieName,MovieDesc,MovieReleaseDate,MovieGenre,MoviePic);
                            movieAllList.add(movie);

                        }
                        movieViewHolder = new MovieAllViewHolder(ViewAllMovies.this, movieAllList);
                        recyclerView.setAdapter(movieViewHolder);
                    }
                });

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllMovies.this, AddMovies.class);
                startActivity(intent);
            }
        });
    }


}
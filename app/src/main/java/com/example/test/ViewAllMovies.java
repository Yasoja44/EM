package com.example.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.Movie;
import com.example.test.ViewHolder.MovieAllViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllMovies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Movie> movieAllList;
    private MovieAllViewHolder movieViewHolder;
    private TextView textView;
    private EditText search;

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

        movieAllList=new ArrayList<>();

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
    }
}
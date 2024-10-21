package com.example.test.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Filters.FilterMoviesUser;
import com.example.test.Model.Movie;
import com.example.test.R;
import com.example.test.ViewAllReviews;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAllViewHolderUser extends RecyclerView.Adapter<MovieAllViewHolderUser.MovieHolder>  implements Filterable {
    private final Context context;
    public ArrayList<Movie> MovieAllList,filterList;
    private FilterMoviesUser filter;



    public MovieAllViewHolderUser(Context context, ArrayList<Movie> movieAllList) {
        this.context = context;
        MovieAllList = movieAllList;
        this.filterList=MovieAllList;
    }

    @Override
    public MovieAllViewHolderUser.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_user_card,parent,false);
        return new MovieAllViewHolderUser.MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieAllViewHolderUser.MovieHolder holder, int position) {
        Movie movie1=MovieAllList.get(position);
        String id=movie1.getId();
        String movieName=movie1.getMovieName();
        String movieDesc=movie1.getMovieDesc();
        String movieReleaseDate=movie1.getMovieReleaseDate();
        String movieGenre=movie1.getMovieGenre();
        String moviePic=movie1.getMoviePic();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        holder.txtMovieName_all.setText(movieName);
        holder.txtMovieGenre_all.setText(movieGenre);
        holder.txtMovieReleaseDate_all.setText(movieReleaseDate);
        Picasso.get().load(moviePic).into(holder.imageMovie_all);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllReviews.class);
                intent.putExtra("movieId", movie1.getId());
                intent.putExtra("movieName", movie1.getMovieName());
                intent.putExtra("moviePic", movie1.getMoviePic());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return MovieAllList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterMoviesUser(this,filterList);
        }
        return filter;
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        TextView txtMovieName_all,txtMovieGenre_all,txtMovieReleaseDate_all;
        ImageView imageMovie_all;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            txtMovieName_all=itemView.findViewById(R.id.movie_name);
            txtMovieGenre_all=itemView.findViewById(R.id.movie_genre);
            imageMovie_all=itemView.findViewById(R.id.item_image);
            txtMovieReleaseDate_all=itemView.findViewById(R.id.movie_release_date);

        }
    }
}

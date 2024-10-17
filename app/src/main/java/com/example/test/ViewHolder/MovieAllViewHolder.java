package com.example.test.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Filters.FilterMovies;
import com.example.test.Filters.FilterViewMovie;
import com.example.test.Model.Movie;
import com.example.test.R;
import com.example.test.ViewAllReviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAllViewHolder extends RecyclerView.Adapter<MovieAllViewHolder.MovieHolder>  implements Filterable {

    private final Context context;
    public ArrayList<Movie> MovieAllList,filterList;
    private FilterMovies filter;


    public MovieAllViewHolder(Context context, ArrayList<Movie> movieAllList) {
        this.context = context;
        MovieAllList = movieAllList;
        this.filterList=MovieAllList;
    }

    @Override
    public MovieAllViewHolder.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_card,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieAllViewHolder.MovieHolder holder, int position) {
        Movie movie1=MovieAllList.get(position);
        String id=movie1.getId();
        String movieName=movie1.getMovieName();
        String movieDesc=movie1.getMovieDesc();
        String movieReleaseDate=movie1.getMovieReleaseDate();
        String movieGenre=movie1.getMovieGenre();
        String moviePic=movie1.getMoviePic();

        holder.txtMovieName_all.setText(movieName);
        holder.txtMovieGenre_all.setText(movieGenre);
        holder.txtMovieReleaseDate_all.setText(movieReleaseDate);
        Picasso.get().load(moviePic).into(holder.imageMovie_all);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllReviews.class);
                intent.putExtra("movieId", movie1.getId());
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
            filter=new FilterMovies(this,filterList);
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

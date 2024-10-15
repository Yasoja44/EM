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

import com.example.test.Filters.FilterViewMovie;
import com.example.test.Model.Movie;
import com.example.test.R;

import java.util.ArrayList;

public class MovieViewHolder extends RecyclerView.Adapter<MovieViewHolder.MovieHolder>  implements Filterable {
    private final Context context;
    public ArrayList<Movie> MovieList,filterList;
    private FilterViewMovie filter;

    public MovieViewHolder(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        MovieList = movieList;
        this.filterList=MovieList;
    }

    @Override
    public MovieViewHolder.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_card,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieViewHolder.MovieHolder holder, int position) {
        Movie movie=MovieList.get(position);
        String id=movie.getId();
        String movieName=movie.getMovieName();
        String movieDesc=movie.getMovieDesc();
        String movieReleaseDate=movie.getMovieReleaseDate();
        String movieGenre=movie.getMovieGenre();

        holder.txtMovieName.setText(movieName);
        holder.txtMovieGenre.setText(movieGenre);
        holder.txtMovieReleaseDate.setText(movieReleaseDate);
        //Picasso.get().load(itemImage).into(holder.imageItem);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewMovie.class);
//                intent.putExtra("itemId", item.getId());
//                intent.putExtra("supplierId", item.getItemSupplierId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterViewMovie(this,filterList);
        }
        return filter;
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        TextView txtMovieName,txtMovieGenre,txtMovieReleaseDate;
        ImageView imageItem;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            txtMovieName=itemView.findViewById(R.id.movie_name);
            txtMovieGenre=itemView.findViewById(R.id.movie_genre);
            txtMovieReleaseDate=itemView.findViewById(R.id.movie_release_date);

        }
    }
}

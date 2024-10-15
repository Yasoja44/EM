package com.example.test.Filters;

import android.widget.Filter;

import com.example.test.Model.Movie;
import com.example.test.ViewHolder.MovieViewHolder;

import java.util.ArrayList;

public class FilterViewMovie extends Filter{
    private MovieViewHolder adapter;
    private ArrayList<Movie> filterList;

    public FilterViewMovie(MovieViewHolder adapter, ArrayList<Movie> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Movie> filterMovies =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getMovieName().toUpperCase().contains(constraint)||
                        filterList.get(i).getMovieGenre().toUpperCase().contains(constraint)){
                    filterMovies.add(filterList.get(i));
                }
            }
            results.count=filterMovies.size();
            results.values=filterMovies;
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.MovieList= (ArrayList<Movie>) results.values;
        adapter.notifyDataSetChanged();
    }
}

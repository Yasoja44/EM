package com.example.test.Filters;

import android.widget.Filter;

import com.example.test.Model.Movie;
import com.example.test.ViewHolder.MovieAllViewHolderUser;

import java.util.ArrayList;

public class FilterMoviesUser extends Filter{
    private MovieAllViewHolderUser adapter;
    private ArrayList<Movie> filterList;

    public FilterMoviesUser(MovieAllViewHolderUser adapter, ArrayList<Movie> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Movie> filterItems =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getMovieName().toUpperCase().contains(constraint)||
                        filterList.get(i).getMovieGenre().toUpperCase().contains(constraint)){
                    filterItems.add(filterList.get(i));
                }
            }
            results.count=filterItems.size();
            results.values=filterItems;
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.MovieAllList= (ArrayList<Movie>) results.values;
        adapter.notifyDataSetChanged();
    }
}

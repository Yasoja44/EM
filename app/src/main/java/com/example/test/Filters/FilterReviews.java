package com.example.test.Filters;

import android.widget.Filter;

import com.example.test.Model.Review;
import com.example.test.ViewHolder.ReviewAllViewHolder;

import java.util.ArrayList;

public class FilterReviews extends Filter {
    private ReviewAllViewHolder adapter;
    private ArrayList<Review> filterList;

    public FilterReviews(ReviewAllViewHolder adapter, ArrayList<Review> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Review> filterItems =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(String.valueOf(filterList.get(i).getReviewRating()).contains(constraint)){
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
        adapter.ReviewAllList= (ArrayList<Review>) results.values;
        adapter.notifyDataSetChanged();
    }
}

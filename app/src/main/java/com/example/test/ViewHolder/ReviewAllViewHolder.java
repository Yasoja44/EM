package com.example.test.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Filters.FilterMovies;
import com.example.test.Filters.FilterReviews;
import com.example.test.Model.Movie;
import com.example.test.Model.Review;
import com.example.test.R;

import java.util.ArrayList;

public class ReviewAllViewHolder extends RecyclerView.Adapter<ReviewAllViewHolder.ReviewHolder>  implements Filterable {
    private final Context context;
    public ArrayList<Review> ReviewAllList,filterList;
    private FilterReviews filter;

    public ReviewAllViewHolder(Context context, ArrayList<Review> reviewAllList) {
        this.context = context;
        ReviewAllList = reviewAllList;
        this.filterList=ReviewAllList;
    }

    @Override
    public ReviewAllViewHolder.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.review_card,parent,false);
        return new ReviewAllViewHolder.ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ReviewAllViewHolder.ReviewHolder holder, int position) {
        Review review1=ReviewAllList.get(position);
        String id=review1.getId();
        String reviewUserId=review1.getUserId();
        String reviewMovieId=review1.getMovieId();
        String reviewDesc=review1.getReviewDesc();
        String reviewRating= String.valueOf(review1.getReviewRating());

        holder.txtReviewUser_all.setText(reviewUserId);
        holder.txtReviewDesc_all.setText(reviewDesc);
        holder.txtReviewRating_all.setText(reviewRating);
        //Picasso.get().load(itemImage).into(holder.imageItem_all);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewItem.class);
//                intent.putExtra("itemId", item1.getId());
//                intent.putExtra("supplierId", item1.getItemSupplierId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return ReviewAllList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterReviews(this,filterList);
        }
        return filter;
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView txtReviewUser_all,txtReviewDesc_all,txtReviewRating_all;
        //ImageView imageItem_all;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            txtReviewUser_all=itemView.findViewById(R.id.review_user);
            txtReviewDesc_all=itemView.findViewById(R.id.review_desc);
            //imageItem_all=itemView.findViewById(R.id.item_image);
            txtReviewRating_all=itemView.findViewById(R.id.review_rating);

        }
    }
}

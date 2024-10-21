package com.example.test.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Filters.FilterMovies;
import com.example.test.Filters.FilterReviews;
import com.example.test.Model.Movie;
import com.example.test.Model.Review;
import com.example.test.R;
import com.example.test.ViewAllMovies;
import com.example.test.ViewAllReviews;
import com.example.test.ViewReview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReviewAllViewHolder extends RecyclerView.Adapter<ReviewAllViewHolder.ReviewHolder>  implements Filterable {
    private final Context context;
    public ArrayList<Review> ReviewAllList,filterList;
    private FilterReviews filter;
    private FirebaseFirestore firestore;
    //String ReviewUsername;



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
        String reviewPic=review1.getReviewPic();

        //db.collection('books').doc('fK3ddutEpD2qQqRMXNW5').get()


        holder.txtReviewUser_all.setText(reviewUserId);
        holder.txtReviewDesc_all.setText(reviewDesc);
        holder.txtReviewRating_all.setText(reviewRating);
        Picasso.get().load(reviewPic).into(holder.imageItem_all);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewReview.class);
                intent.putExtra("reviewUserId", reviewUserId);
                intent.putExtra("reviewMovieId", reviewMovieId);
                intent.putExtra("reviewDesc", reviewDesc);
                intent.putExtra("reviewRating", reviewRating);
                intent.putExtra("reviewPic", reviewPic);
                context.startActivity(intent);
            }
        });


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
        ImageView imageItem_all;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            txtReviewUser_all=itemView.findViewById(R.id.review_user);
            txtReviewDesc_all=itemView.findViewById(R.id.review_desc);
            imageItem_all=itemView.findViewById(R.id.item_image);
            txtReviewRating_all=itemView.findViewById(R.id.review_rating);

            firestore= FirebaseFirestore.getInstance();



        }
    }
}



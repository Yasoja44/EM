package com.example.test.Model;

public class Review {

    private String id;
    private String UserId;
    private String MovieId;
    private String ReviewDesc;
    private double ReviewRating;
    private String ReviewPic;

    public Review(String id, String userId, String movieId, String reviewDesc, double reviewRating, String reviewPic) {
        this.id = id;
        UserId = userId;
        MovieId = movieId;
        ReviewDesc = reviewDesc;
        ReviewRating = reviewRating;
        ReviewPic = reviewPic;
    }

    public String getReviewPic() {
        return ReviewPic;
    }

    public void setReviewPic(String reviewPic) {
        ReviewPic = reviewPic;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getReviewDesc() {
        return ReviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        ReviewDesc = reviewDesc;
    }

    public double getReviewRating() {
        return ReviewRating;
    }

    public void setReviewRating(double reviewRating) {
        ReviewRating = reviewRating;
    }
}

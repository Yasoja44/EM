package com.example.test.Model;

public class Movie {
    private String id;
    private String MovieName;
    private String MovieDesc;
    private String MovieReleaseDate;
    private String MovieGenre;

    public Movie(String id, String movieName, String movieDesc, String movieReleaseDate, String movieGenre) {
        this.id = id;
        MovieName = movieName;
        MovieDesc = movieDesc;
        MovieReleaseDate = movieReleaseDate;
        MovieGenre = movieGenre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieDesc() {
        return MovieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        MovieDesc = movieDesc;
    }

    public String getMovieReleaseDate() {
        return MovieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        MovieReleaseDate = movieReleaseDate;
    }

    public String getMovieGenre() {
        return MovieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        MovieGenre = movieGenre;
    }
}

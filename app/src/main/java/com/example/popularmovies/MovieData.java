package com.example.popularmovies;

public class MovieData {

    private String originalTitle;
    private String moviePosterImage;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public MovieData() {
    }

    public MovieData(String originalTitle, String moviePosterImage, String overview, String voteAverage, String releaseDate) {
        this.originalTitle = originalTitle;
        this.moviePosterImage = moviePosterImage;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePosterImage() {
        return moviePosterImage;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setMoviePosterImage(String moviePosterImage) {
        this.moviePosterImage = moviePosterImage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

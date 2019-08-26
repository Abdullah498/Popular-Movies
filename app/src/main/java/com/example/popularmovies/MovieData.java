package com.example.popularmovies;

public class MovieData {

    private String originalTitle;
    private String moviePosterImage;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String id;
    private String key,name;
    private String author,content;

    public MovieData(String moviePosterImage){
        this.moviePosterImage=moviePosterImage;
    }

    public MovieData(String key, String name) {
        this.key=key;
        this.name=name;
    }

    public MovieData(String author, String content, String url) {
        this.author=author;
        this.content=content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieData() {
    }

    public MovieData(String originalTitle, String moviePosterImage, String overview, String voteAverage, String releaseDate , String id ) {
        this.originalTitle = originalTitle;
        this.moviePosterImage = moviePosterImage;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

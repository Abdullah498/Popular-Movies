package com.example.popularmovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int RomId;
    private String originalTitle;
    @ColumnInfo(name = "poster_path")
    private String moviePosterImage;
    private String overview;
    @ColumnInfo(name = "vote_average")
    private String voteAverage;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo(name ="movie_id")
    private String id;
    private String key,name;
    private String author,content;

    public MovieData(int romId, String originalTitle, String moviePosterImage, String overview, String voteAverage, String releaseDate, String id) {
        this.RomId = romId;
        this.originalTitle = originalTitle;
        this.moviePosterImage = moviePosterImage;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    @Ignore
    public MovieData(String moviePosterImage){
        this.moviePosterImage=moviePosterImage;
    }

    @Ignore
    public MovieData(String key, String name) {
        this.key=key;
        this.name=name;
    }

    @Ignore
    public MovieData(String author, String content, String url) {
        this.author=author;
        this.content=content;
    }

    @Ignore
    public MovieData(String originalTitle, String moviePosterImage, String overview, String voteAverage, String releaseDate , String id ) {
        this.originalTitle = originalTitle;
        this.moviePosterImage = moviePosterImage;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
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

    public int getRomId() {
        return RomId;
    }

    public void setRomId(int romId) {
        RomId = romId;
    }
}
